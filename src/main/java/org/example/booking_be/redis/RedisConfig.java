package org.example.booking_be.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration redisConfig =new RedisStandaloneConfiguration();

        redisConfig.setHostName("redis-18103.c239.us-east-1-2.ec2.cloud.redislabs.com");
        redisConfig.setPort(18103);
        redisConfig.setUsername("default");
        redisConfig.setPassword("Z383ZMjqNdoJmLwEBtZlSQ1Cgk3WWKSc");

        LettuceClientConfiguration clientConfig =
                LettuceClientConfiguration.builder()
                        .build();

        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

    @Bean(name = "blacklistRedisTemplate")
    public RedisTemplate<String, String> blacklistRedisTemplate(
            LettuceConnectionFactory factory
    ) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());

        return template;
    }
}
