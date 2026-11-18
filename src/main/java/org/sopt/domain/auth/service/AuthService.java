package org.sopt.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.domain.entity.RefreshToken;
import org.sopt.domain.auth.dto.AuthTokens;
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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long signup(String name, String email, String password) {
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = new Member(name, email, passwordEncoder.encode(password));
        return memberRepository.save(member).getId();
    }

    @Transactional
    public AuthTokens login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        if (member.isDeleted()) {
            throw new MemberWithdrawnException();
        }

        JwtToken accessToken = jwtProvider.createAccessToken(member.getId());
        JwtToken refreshToken = jwtProvider.createRefreshToken(member.getId());

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
}
