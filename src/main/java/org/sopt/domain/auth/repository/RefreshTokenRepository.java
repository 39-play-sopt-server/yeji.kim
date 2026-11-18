package org.sopt.domain.auth.repository;

import org.sopt.domain.auth.domain.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByTokenId(String tokenId);

    void delete(RefreshToken refreshToken);

    void deleteByMemberId(Long memberId);
}
