package org.sopt.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private static final String TOKEN_TYPE_CLAIM = "tokenType";

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtProvider(
            SecretKey secretKey,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.secretKey = secretKey;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public JwtToken createAccessToken(Long memberId) {
        return createToken(
                memberId,
                JwtTokenType.ACCESS,
                accessTokenExpiration
        );
    }

    public JwtToken createRefreshToken(Long memberId) {
        return createToken(
                memberId,
                JwtTokenType.REFRESH,
                refreshTokenExpiration
        );
    }

    public Long getMemberId(String token) {
        return getPayload(token, JwtTokenType.ACCESS).memberId();
    }

    public JwtTokenPayload getPayload(
            String token,
            JwtTokenType expectedType
    ) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .require(TOKEN_TYPE_CLAIM, expectedType.name())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        if (!StringUtils.hasText(claims.getId()) || claims.getExpiration() == null) {
            throw new MalformedJwtException("필수 토큰 정보가 없습니다.");
        }

        Long memberId;
        try {
            memberId = Long.valueOf(claims.getSubject());
        } catch (NumberFormatException e) {
            throw new MalformedJwtException("회원 ID가 올바르지 않습니다.");
        }

        if (memberId <= 0) {
            throw new MalformedJwtException("회원 ID가 올바르지 않습니다.");
        }

        return new JwtTokenPayload(
                memberId,
                claims.getId(),
                claims.getExpiration().toInstant()
        );
    }

    private JwtToken createToken(
            Long memberId,
            JwtTokenType tokenType,
            long expirationMillis
    ) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(expirationMillis);

        String token = Jwts.builder()
                .subject(String.valueOf(memberId))
                .id(UUID.randomUUID().toString())
                .claim(TOKEN_TYPE_CLAIM, tokenType.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();

        return new JwtToken(token, expiresAt);
    }
}
