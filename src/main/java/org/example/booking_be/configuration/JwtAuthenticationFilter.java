package org.example.booking_be.configuration;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.booking_be.entity.User;
//import org.example.booking_be.redis.RedisService;
import org.example.booking_be.reponsitory.UserReponsitory;
import org.example.booking_be.util.JwtUtil;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserReponsitory userRepository;
//    private final RedisService redisService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 1️⃣ Không có token → bỏ qua
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        // 2️⃣ Token sai / hết hạn
        if (!jwtUtil.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3️⃣ Token đã logout (blacklist)
//        if (redisService.isAccessTokenBlacklisted(token)) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        // 4️⃣ Lấy email từ token
        String email = jwtUtil.extractEmail(token);

        // 5️⃣ Chưa có authentication trong context
        if (email != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userRepository.findByEmail(email).orElse(null);

            if (user != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                "ROLE_" + user.getRole().name()
                                        )
                                )
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}