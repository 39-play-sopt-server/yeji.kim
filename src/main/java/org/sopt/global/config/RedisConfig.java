package org.sopt.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(
            RedisConnectionFactory connectionFactory
    ) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer serializer = new StringRedisSerializer();

        // Redis를 연결합니다.
        redisTemplate.setConnectionFactory(connectionFactory);

        // Key-Value 형태로 직렬화를 수행합니다.
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);

        // Hash Key-Value 형태로 직렬화를 수행합니다.
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);

        return redisTemplate;
    }
}
