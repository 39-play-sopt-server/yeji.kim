package org.sopt.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisHashCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedisHandler {

    private final RedisTemplate<String, String> redisTemplate;

    // 만료 시간과 함께 저장
    public void set(String key, String value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    // 존재 여부 확인
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    // 키 삭제
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // Hash 항목을 만료 시간과 함께 저장
    public void hashSet(
            String key,
            String field,
            String value,
            Duration ttl
    ) {
        long ttlMillis = ttl.toMillis();

        if (ttlMillis <= 0) {
            throw new IllegalArgumentException("TTL은 0밀리초보다 커야 합니다.");
        }

        redisTemplate.<String, String>opsForHash().putAndExpire(
                key,
                Map.of(field, value),
                RedisHashCommands.HashFieldSetOption.UPSERT,
                Expiration.from(ttl)
        );
    }

    // Hash 항목 조회
    public Optional<String> hashGet(String key, String field) {
        return Optional.ofNullable(
                redisTemplate.<String, String>opsForHash().get(key, field)
        );
    }

    // Hash 항목을 조회하면서 삭제
    public Optional<String> hashGetAndDelete(String key, String field) {
        List<String> values = redisTemplate
                .<String, String>opsForHash()
                .getAndDelete(key, List.of(field));

        if (values == null || values.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(values.get(0));
    }

    // Hash 항목 삭제
    public void hashDelete(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }
}
