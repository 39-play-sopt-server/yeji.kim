package org.sopt.global.security.jwt;

import java.time.Instant;

public record JwtToken(
        String value,
        Instant expiresAt
) {
}
