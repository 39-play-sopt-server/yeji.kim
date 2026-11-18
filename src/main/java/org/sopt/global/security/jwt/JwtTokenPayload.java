package org.sopt.global.security.jwt;

import java.time.Instant;

public record JwtTokenPayload(
        Long memberId,
        String tokenId,
        Instant expiresAt
) {
}
