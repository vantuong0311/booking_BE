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
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .domain("localhost")     // 🔥 BẮT BUỘC
                .maxAge(jwtUtil.getRemainingTime(refreshToken) / 1000)
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
        // 1️⃣ Lấy refresh token từ cookie
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        // 2️⃣ Xoá refresh token trong Redis
        if (refreshToken != null && jwtUtil.isTokenValid(refreshToken)) {
            String email = jwtUtil.extractEmail(refreshToken);
            userRepository.findByEmail(email)
                    .ifPresent(user ->
                            redisService.deleteRefreshToken(user.getId())
                    );
        }

        // 3️⃣ XOÁ COOKIE — PHẢI GIỐNG 100%
        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false) // ⚠️ GIỐNG LOGIN
                .sameSite("Strict") // ⚠️ GIỐNG LOGIN
                .path("/") // ⚠️ GIỐNG LOGIN
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ApiResponse.<Void>builder()
                .message("Logout successfully")
                .build();
    }


    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

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

        // kiểm tra refresh token trong redis
        if (!redisService.isRefreshTokenValid(user.getId(), refreshToken)) {
            throw new RuntimeException("Refresh token revoked");
        }

        // ================= ROTATE REFRESH TOKEN =================

        // 1. xóa refresh token cũ
        redisService.deleteRefreshToken(user.getId());

        // 2. tạo refresh token mới
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        redisService.saveRefreshToken(
                user.getId(),
                newRefreshToken,
                jwtUtil.getRemainingTime(newRefreshToken)
        );

        // 3. set lại cookie refresh token
        ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
                .httpOnly(true)
                .secure(false) // true nếu dùng https
                .sameSite("Strict")
                .path("/")
                .maxAge(jwtUtil.getRemainingTime(newRefreshToken) / 1000)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // ================= CẤP ACCESS TOKEN MỚI =================

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
