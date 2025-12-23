package org.example.booking_be.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.example.booking_be.configuration.JWTConfig;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;



@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JWTConfig jwtConfig;

    // ===== KEY =====
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    // ===== ACCESS TOKEN =====
    public String generateAccessToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + jwtConfig.getExpiration())
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ===== REFRESH TOKEN =====
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpiration())
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ===== VALIDATE =====
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    // ===== EXTRACT =====
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

//    public String extractRole(String token) {
//        return extractAllClaims(token).get("role", String.class);
//    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // ===== REMAINING TIME =====
//    public long getRemainingTime(String token) {
//        return extractExpiration(token).getTime() - System.currentTimeMillis();
//    }

    // ===== PARSE =====
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
