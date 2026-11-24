package org.sopt.domain.auth.dto.response;

import org.sopt.global.security.jwt.JwtToken;

public record AuthTokensResponse(
        JwtToken accessToken,
        JwtToken refreshToken
) {
}
