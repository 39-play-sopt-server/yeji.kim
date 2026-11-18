package org.sopt.domain.auth.repository.impl;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.domain.entity.RefreshToken;
import org.sopt.domain.auth.repository.RefreshTokenJpaRepository;
import org.sopt.domain.auth.repository.RefreshTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository jpaRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return jpaRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByTokenId(String tokenId) {
        return jpaRepository.findByTokenId(tokenId);
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        jpaRepository.delete(refreshToken);
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        jpaRepository.deleteByMemberId(memberId);
    }
}
