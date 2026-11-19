package org.sopt.global.security.oauth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.dto.response.AuthTokensResponse;
import org.sopt.domain.auth.service.AuthService;
import org.sopt.global.exception.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;


@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");

        try {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

            AuthTokensResponse tokens = authService.socialLogin(
                    oauthToken.getAuthorizedClientRegistrationId(),
                    oauthToken.getPrincipal().getAttributes()
            );

            ResponseCookie cookie = ResponseCookie
                    .from("refreshToken", tokens.refreshToken().value())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/auth")
                    .maxAge(Duration.between(Instant.now(), tokens.refreshToken().expiresAt()))
                    .build();

            response.setHeader(
                    HttpHeaders.AUTHORIZATION,
                    "Bearer " + tokens.accessToken().value()
            );
            response.addHeader(
                    HttpHeaders.SET_COOKIE,
                    cookie.toString()
            );
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (BusinessException e) {
            response.setStatus(e.getErrorCode().getHttpStatus().value());
        } catch (OAuth2AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } finally {
            SecurityContextHolder.clearContext();

            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
    }
}
