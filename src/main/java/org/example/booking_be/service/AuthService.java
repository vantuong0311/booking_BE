package org.example.booking_be.service;

import lombok.RequiredArgsConstructor;


import org.example.booking_be.dto.responce.RegisterResponse;
import org.example.booking_be.entity.User;
//import org.example.booking_be.redis.RedisService;
import org.example.booking_be.mapper.UserMapper;
import org.example.booking_be.reponsitory.UserReponsitory;
import org.example.booking_be.util.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserReponsitory userReponsitory;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
//    private final RedisService redisService;

    // ================= LOGIN =================
    public String login(String email, String password) {

        User user = userReponsitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return jwtUtil.generateAccessToken(
                user.getEmail(),
                user.getRole().name()
        );
    }
//    public User register(RegisterRequest request) {
//        User user = userMapper.toEntity(request);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        return  userReponsitory.save(user);
//
//
//    }

    // ================= LOGOUT =================
//    public void logout(String accessToken) {
//
//        long remainingTime = jwtUtil.getRemainingTime(accessToken);
//
//        redisService.blacklistAccessToken(
//                accessToken,
//                remainingTime
//        );
//    }
}