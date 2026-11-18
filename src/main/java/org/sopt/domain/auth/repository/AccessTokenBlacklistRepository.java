package org.sopt.domain.auth.repository;

import org.sopt.domain.auth.domain.entity.AccessTokenBlacklist;

public interface AccessTokenBlacklistRepository {

    AccessTokenBlacklist save(AccessTokenBlacklist token);

    boolean existByTokenId(String tokenId);
}
