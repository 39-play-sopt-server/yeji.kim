package org.sopt.domain.auth.repository;

import org.sopt.domain.auth.domain.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByMemberIdAndTokenId(
            Long memberId,
            String tokenId
    );

    Optional<RefreshToken> findAndDelete(Long memberId, String tokenId);

    void delete(RefreshToken refreshToken);

    void deleteByMemberId(Long memberId);
}
