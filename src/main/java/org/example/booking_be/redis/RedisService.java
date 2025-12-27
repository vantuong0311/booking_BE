package org.example.booking_be.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    // ⚠️ BẮT BUỘC Qualifier ở constructor
    public RedisService(
            @Qualifier("blacklistRedisTemplate")
            RedisTemplate<String, String> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
    }

    private static final String ACCESS_TOKEN_BLACKLIST_PREFIX = "blacklist:access:";
    private static final String REFRESH_TOKEN_PREFIX = "refresh:";

    // ================= ACCESS TOKEN =================

    public void blacklistAccessToken(String accessToken, long expirationMillis) {
        String key = ACCESS_TOKEN_BLACKLIST_PREFIX + accessToken;

        redisTemplate.opsForValue().set(
                key,
                "BLACKLISTED",
                expirationMillis,
                TimeUnit.MILLISECONDS
        );
    }

    public boolean isAccessTokenBlacklisted(String accessToken) {
        String key = ACCESS_TOKEN_BLACKLIST_PREFIX + accessToken;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    // ================= REFRESH TOKEN =================

    public void saveRefreshToken(String userId, String refreshToken, long expirationMillis) {
        String key = REFRESH_TOKEN_PREFIX + userId;

        redisTemplate.opsForValue().set(
                key,
                refreshToken,
                expirationMillis,
                TimeUnit.MILLISECONDS
        );
    }

    public void deleteRefreshToken(String userId) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + userId);
    }

    public boolean isRefreshTokenValid(String userId, String refreshToken) {
        String storedToken =
                redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + userId);

        return storedToken != null && storedToken.equals(refreshToken);
    }

}