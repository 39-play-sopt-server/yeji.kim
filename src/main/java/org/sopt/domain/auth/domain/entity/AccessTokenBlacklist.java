package org.sopt.domain.auth.domain.entity;

import java.time.Instant;

public record AccessTokenBlacklist(
        String tokenId,
        Instant expiresAt

) {
}
