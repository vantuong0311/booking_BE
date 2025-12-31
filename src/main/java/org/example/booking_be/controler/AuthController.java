package org.example.booking_be.controler;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.booking_be.dto.ApiResponse;
import org.example.booking_be.dto.request.LoginRequest;

import org.example.booking_be.dto.request.UserCreateRequest;
import org.example.booking_be.dto.responce.AuthResponse;
import org.example.booking_be.dto.responce.RegisterResponse;
import org.example.booking_be.dto.responce.UserResponse;
import org.example.booking_be.entity.User;
//import org.example.booking_be.redis.RedisService;
import org.example.booking_be.redis.RedisService;
import org.example.booking_be.reponsitory.UserReponsitory;
import org.example.booking_be.service.AuthService;
import org.example.booking_be.service.UserService;
import org.example.booking_be.util.JwtUtil;
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
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        // Tạo token
        String accessToken = jwtUtil.generateAccessToken(
                user.getEmail(),
                user.getRole().name()
        );

        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        // Lưu refresh token vào Redis
        redisService.saveRefreshToken(
                user.getId(),
                refreshToken,
                jwtUtil.getRemainingTime(refreshToken)
        );

        return ApiResponse.<AuthResponse>builder()
                .result(new AuthResponse(accessToken, refreshToken))
                .build();
    }

    // ================= LOGOUT =================
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }

        String accessToken = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(accessToken)) {
            throw new RuntimeException("Invalid token");
        }

        long remainingTime = jwtUtil.getRemainingTime(accessToken);

        redisService.blacklistAccessToken(accessToken, remainingTime);

        // 🔥 XÓA refresh token
        String email = jwtUtil.extractEmail(accessToken);
        userRepository.findByEmail(email)
                .ifPresent(user -> redisService.deleteRefreshToken(user.getId()));

        return ApiResponse.<Void>builder()
                .message("Logout successfully")
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@RequestBody String refreshToken) {

        if (!jwtUtil.isTokenValid(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtUtil.extractEmail(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // kiểm tra refresh token trong Redis
        if (!redisService.isRefreshTokenValid(user.getId(), refreshToken)) {
            throw new RuntimeException("Refresh token revoked");
        }

        String newAccessToken =
                jwtUtil.generateAccessToken(
                        user.getEmail(),
                        user.getRole().name()
                );

        return ApiResponse.<AuthResponse>builder()
                .result(new AuthResponse(newAccessToken, refreshToken))
                .build();
    }
//    @PostMapping("/register")
//    public ApiResponse<RegisterResponse> register(
//            @RequestBody RegisterRequest request
//    ) {
//        return ApiResponse.<RegisterResponse>builder()
//                .result(authService.register(request))
//                .message("Đăng ký thành công")
//                .build();
//    }
        @PostMapping("/register")
        public ApiResponse<UserResponse> createUser(@RequestBody UserCreateRequest request) {
            return ApiResponse.<UserResponse>builder()
                    .result(userService.createUser(request))
                    .build();
        }

}
