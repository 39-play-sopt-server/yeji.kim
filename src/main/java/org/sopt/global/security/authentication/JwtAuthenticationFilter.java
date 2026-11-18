package org.sopt.global.security.authentication;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.repository.AccessTokenBlacklistRepository;
import org.sopt.domain.member.repository.MemberRepository;
import org.sopt.global.security.jwt.JwtProvider;
import org.sopt.global.security.jwt.JwtTokenPayload;
import org.sopt.global.security.jwt.JwtTokenType;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final AccessTokenBlacklistRepository accessTokenBlacklistRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null) {
            try {
                JwtTokenPayload payload = jwtProvider.getPayload(token, JwtTokenType.ACCESS);

                boolean blacklisted = accessTokenBlacklistRepository
                        .existByTokenId(payload.tokenId());

                boolean activeMember = memberRepository
                        .existsByIdAndDeletedFalse(payload.memberId());

                if (!blacklisted && activeMember) {
                    // 인증 완료된 Authentication 생성 후 SecurityContext에 저장
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(payload.memberId(), null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } catch (JwtException | IllegalArgumentException e) {
                // 유효하지 않은 토큰이면 인증 정보를 저장하지 않음
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
