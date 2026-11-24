package org.sopt.domain.auth.repository.impl;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.domain.entity.RefreshToken;
import org.sopt.domain.auth.repository.RefreshTokenRepository;
import org.sopt.global.redis.RedisHandler;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private static final String KEY_PREFIX = "auth:refresh:";

    private final RedisHandler redisHandler;

    @Override
    public RefreshToken save(RefreshToken token) {
        Duration ttl = Duration.between(
                Instant.now(),
                token.expiresAt()
        );

        redisHandler.hashSet(
                key(token.memberId()),
                token.tokenId(),
                token.expiresAt().toString(),
                ttl
        );

        return token;
    }

    @Override
    public Optional<RefreshToken> findByMemberIdAndTokenId(
            Long memberId,
            String tokenId
    ) {
        return redisHandler.hashGet(key(memberId), tokenId)
                .map(expiresAt -> new RefreshToken(
                        tokenId,
                        memberId,
                        Instant.parse(expiresAt)
                ));
    }

    @Override
    public Optional<RefreshToken> findAndDelete(
            Long memberId,
            String tokenId
    ) {
        return redisHandler.hashGetAndDelete(key(memberId), tokenId)
                .map(expiresAt -> new RefreshToken(
                        tokenId,
                        memberId,
                        Instant.parse(expiresAt)
                ));
    }

    @Override
    public void delete(RefreshToken token) {
        redisHandler.hashDelete(
                key(token.memberId()),
                token.tokenId()
        );
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        redisHandler.delete(key(memberId));
    }

    private String key(Long memberId) {
        return KEY_PREFIX + memberId;
    }
}
