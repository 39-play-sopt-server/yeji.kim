package org.sopt.domain.auth.service;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.code.AuthErrorCode;
import org.sopt.domain.auth.domain.entity.AccessTokenBlacklist;
import org.sopt.domain.auth.domain.entity.RefreshToken;
import org.sopt.domain.auth.dto.AuthTokens;
import org.sopt.domain.auth.exception.AuthValidationException;
import org.sopt.domain.auth.exception.InvalidRefreshTokenException;
import org.sopt.domain.auth.repository.AccessTokenBlacklistRepository;
import org.sopt.domain.auth.repository.RefreshTokenRepository;
import org.sopt.domain.member.domain.entity.Member;
import org.sopt.domain.member.exception.MemberWithdrawnException;
import org.sopt.domain.member.repository.MemberRepository;
import org.sopt.global.security.jwt.JwtProvider;
import org.sopt.global.security.jwt.JwtToken;
import org.sopt.global.security.jwt.JwtTokenPayload;
import org.sopt.global.security.jwt.JwtTokenType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenBlacklistRepository accessTokenBlacklistRepository;

    @Transactional
    public Long signup(String name, String email, String password) {
        if (memberRepository.existsByEmail(email)) {
            throw new AuthValidationException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Member member = new Member(name, email, passwordEncoder.encode(password));
        return memberRepository.save(member).getId();
    }

    @Transactional
    public AuthTokens login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthValidationException(AuthErrorCode.INVALID_CREDENTIALS));

        if (member.getPassword() == null
                || !passwordEncoder.matches(password, member.getPassword())) {
            throw new AuthValidationException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        if (member.isDeleted()) {
            throw new MemberWithdrawnException();
        }

        return issueTokens(member.getId());
    }

    @Transactional
    public AuthTokens refresh(String token) {
        JwtTokenPayload payload;

        try {
            payload = jwtProvider.getPayload(token, JwtTokenType.REFRESH);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidRefreshTokenException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        RefreshToken storedToken = refreshTokenRepository
                .findByTokenId(payload.tokenId())
                .orElseThrow(() -> new InvalidRefreshTokenException(
                        AuthErrorCode.INVALID_REFRESH_TOKEN
                ));

        if (!storedToken.getMemberId().equals(payload.memberId()) || !storedToken.getExpiresAt().isAfter(Instant.now())) {
            throw new InvalidRefreshTokenException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        Member member = memberRepository.findById(payload.memberId())
                .orElseThrow(() -> new InvalidRefreshTokenException(
                        AuthErrorCode.INVALID_REFRESH_TOKEN
                ));

        if (member.isDeleted()) {
            throw new MemberWithdrawnException();
        }

        refreshTokenRepository.delete(storedToken);

        return issueTokens(member.getId());
    }

    @Transactional
    public void logout(String accessToken, String refreshToken) {
        JwtTokenPayload payload;

        try {
            payload = jwtProvider.getPayload(accessToken, JwtTokenType.ACCESS);
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthValidationException(AuthErrorCode.INVALID_ACCESS_TOKEN);
        }

        memberRepository.findWithLockById(payload.memberId())
                .orElseThrow(() -> new AuthValidationException(AuthErrorCode.INVALID_ACCESS_TOKEN));

        if (!accessTokenBlacklistRepository.existByTokenId(payload.tokenId())) {
            accessTokenBlacklistRepository.save(
                    new AccessTokenBlacklist(payload.tokenId(), payload.expiresAt())
            );
        }

        revokeRefreshToken(refreshToken, payload.memberId());
    }

    private AuthTokens issueTokens(Long memberId) {
        JwtToken accessToken = jwtProvider.createAccessToken(memberId);
        JwtToken refreshToken = jwtProvider.createRefreshToken(memberId);

        JwtTokenPayload payload = jwtProvider.getPayload(
                refreshToken.value(),
                JwtTokenType.REFRESH
        );

        refreshTokenRepository.save(
                new RefreshToken(
                        payload.tokenId(),
                        payload.memberId(),
                        payload.expiresAt()
                )
        );

        return new AuthTokens(accessToken, refreshToken);
    }

    private void revokeRefreshToken(String token, Long memberId) {
        if (!StringUtils.hasText(token)) {
            return;
        }

        JwtTokenPayload payload;

        try {
            payload = jwtProvider.getPayload(token, JwtTokenType.REFRESH);
        } catch (JwtException | IllegalArgumentException e) {
            return;
        }

        if (!payload.memberId().equals(memberId)) {
            throw new InvalidRefreshTokenException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        refreshTokenRepository.findByTokenId(payload.tokenId())
                .ifPresent(refreshToken -> {
                    if (!refreshToken.getMemberId().equals(memberId)) {
                        throw new InvalidRefreshTokenException(AuthErrorCode.INVALID_REFRESH_TOKEN);
                    }

                    refreshTokenRepository.delete(refreshToken);
                });
    }
}
