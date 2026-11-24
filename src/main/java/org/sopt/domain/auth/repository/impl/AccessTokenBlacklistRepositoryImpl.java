package org.sopt.domain.auth.repository.impl;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.domain.entity.AccessTokenBlacklist;
import org.sopt.domain.auth.repository.AccessTokenBlacklistRepository;
import org.sopt.global.redis.RedisHandler;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;

@Repository
@RequiredArgsConstructor
public class AccessTokenBlacklistRepositoryImpl implements AccessTokenBlacklistRepository {

    private static final String KEY_PREFIX = "auth:blacklist:";

    private final RedisHandler redisHandler;

    @Override
    public AccessTokenBlacklist save(AccessTokenBlacklist token) {

        Duration ttl = Duration.between(
                Instant.now(),
                token.expiresAt()
        );

        if (!ttl.isNegative() && !ttl.isZero()) {
            redisHandler.set(
                    KEY_PREFIX + token.tokenId(),
                    "revoked",
                    ttl
            );
        }

        return token;
    }

    @Override
    public boolean existByTokenId(String tokenId) {
        return redisHandler.exists(KEY_PREFIX + tokenId);
    }
}
