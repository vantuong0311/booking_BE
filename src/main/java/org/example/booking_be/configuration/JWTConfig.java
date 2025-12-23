package org.example.booking_be.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JWTConfig {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-expiration}")
    private long expiration;
    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

}
