package org.example.booking_be.controler;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.booking_be.dto.ApiResponse;
import org.example.booking_be.dto.request.LoginRequest;

import org.example.booking_be.dto.request.UserCreateRequest;
import org.example.booking_be.dto.responce.AuthResponse;

import org.example.booking_be.dto.responce.UserResponse;
import org.example.booking_be.entity.User;
import org.example.booking_be.redis.RedisService;
import org.example.booking_be.reponsitory.UserReponsitory;
import org.example.booking_be.service.UserService;
import org.example.booking_be.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserReponsitory userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final UserService userService;

    // ================= LOGIN =================
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String accessToken = jwtUtil.generateAccessToken(
                user.getEmail(),
                user.getRole().name()
        );

        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        // lưu refresh token vào Redis
        redisService.saveRefreshToken(
                user.getId(),
                refreshToken,
                jwtUtil.getRemainingTime(refreshToken)
        );

        // 🔥 set refresh token vào HttpOnly cookie
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false) // true khi dùng https
                .path("/")
                .maxAge(jwtUtil.getRemainingTime(refreshToken) / 1000)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponse.<AuthResponse>builder()
                .result(new AuthResponse(accessToken))
                .build();
    }


    // ================= LOGOUT =================
    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }

        String accessToken = authHeader.substring(7);
        long remainingTime = jwtUtil.getRemainingTime(accessToken);

        redisService.blacklistAccessToken(accessToken, remainingTime);

        String email = jwtUtil.extractEmail(accessToken);
        userRepository.findByEmail(email)
                .ifPresent(user -> redisService.deleteRefreshToken(user.getId()));

        // 🔥 xóa cookie
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponse.<Void>builder()
                .message("Logout successfully")
                .build();
    }


    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(HttpServletRequest request) {

        String refreshToken = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null || !jwtUtil.isTokenValid(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtUtil.extractEmail(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!redisService.isRefreshTokenValid(user.getId(), refreshToken)) {
            throw new RuntimeException("Refresh token revoked");
        }

        String newAccessToken = jwtUtil.generateAccessToken(
                user.getEmail(),
                user.getRole().name()
        );

        return ApiResponse.<AuthResponse>builder()
                .result(new AuthResponse(newAccessToken))
                .build();
    }


        @PostMapping("/register")
        public ApiResponse<UserResponse> createUser(@RequestBody UserCreateRequest request) {
            return ApiResponse.<UserResponse>builder()
                    .result(userService.createUser(request))
                    .build();
        }

}
