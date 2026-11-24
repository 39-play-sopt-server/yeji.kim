package org.sopt.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
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

    // 값 조회
    public Optional<String> get(String key) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(key)
        );
    }

    // 값을 반환하면서 삭제
    public Optional<String> getAndDelete(String key) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().getAndDelete(key)
        );
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

        redisTemplate.execute(
                HASH_SET_WITH_TTL,
                List.of(key),
                field,
                value,
                Long.toString(ttlMillis)
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
        return Optional.ofNullable(
                redisTemplate.execute(
                        HASH_GET_AND_DELETE,
                        List.of(key),
                        field
                )
        );
    }

    // Hash 항목 삭제
    public void hashDelete(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    private static final DefaultRedisScript<Long> HASH_SET_WITH_TTL =
            new DefaultRedisScript<>("""
                    redis.call('HSET', KEYS[1], ARGV[1], ARGV[2])
                    redis.call('HPEXPIRE', KEYS[1], ARGV[3], 'FIELDS', 1, ARGV[1])
                    return 1
                    """, Long.class);

    private static final DefaultRedisScript<String> HASH_GET_AND_DELETE =
            new DefaultRedisScript<>("""
                    local value = redis.call('HGET', KEYS[1], ARGV[1])
                    if value then
                        redis.call('HDEL', KEYS[1], ARGV[1])
                    end
                    return value
                    """, String.class);
}
