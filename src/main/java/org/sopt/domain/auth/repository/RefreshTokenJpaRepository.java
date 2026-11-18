package org.sopt.domain.auth.repository;

import jakarta.persistence.LockModeType;
import org.sopt.domain.auth.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<RefreshToken> findByTokenId(String tokenId);

    void deleteByMemberId(Long memberId);
}
