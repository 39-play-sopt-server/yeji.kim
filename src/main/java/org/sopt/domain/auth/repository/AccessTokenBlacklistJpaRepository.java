package org.sopt.domain.auth.repository;

import org.sopt.domain.auth.domain.entity.AccessTokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenBlacklistJpaRepository extends JpaRepository<AccessTokenBlacklist, String> {
}
