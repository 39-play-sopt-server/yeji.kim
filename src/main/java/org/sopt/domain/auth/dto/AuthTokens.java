package org.sopt.domain.auth.dto;

import org.sopt.global.security.jwt.JwtToken;

public record AuthTokens(
        JwtToken accessToken,
        JwtToken refreshToken
) {
}
