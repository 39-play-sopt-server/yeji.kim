package org.sopt.domain.auth.repository.impl;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.domain.entity.AccessTokenBlacklist;
import org.sopt.domain.auth.repository.AccessTokenBlacklistJpaRepository;
import org.sopt.domain.auth.repository.AccessTokenBlacklistRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccessTokenBlacklistRepositoryImpl implements AccessTokenBlacklistRepository {

    private final AccessTokenBlacklistJpaRepository jpaRepository;

    @Override
    public AccessTokenBlacklist save(AccessTokenBlacklist token) {
        return jpaRepository.save(token);
    }

    @Override
    public boolean existByTokenId(String tokenId) {
        return jpaRepository.existsById(tokenId);
    }
}
