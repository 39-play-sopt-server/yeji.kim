package org.sopt.domain.auth.domain.entity;

import java.time.Instant;

public record RefreshToken(
        String tokenId,
        Long memberId,
        Instant expiresAt
) {
}
