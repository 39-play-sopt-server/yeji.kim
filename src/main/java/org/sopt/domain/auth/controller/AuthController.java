package org.sopt.domain.auth.controller;

import org.sopt.domain.auth.dto.AuthTokens;
import org.sopt.domain.auth.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public record SignupRequest(String name, String email, String password) {
    }

    public record LoginRequest(String email, String password) {
    }

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(
            @RequestBody SignupRequest request
    ) {
        return ResponseEntity.ok(
                authService.signup(
                        request.name(),
                        request.email(),
                        request.password()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequest request
    ) {
        AuthTokens tokens = authService.login(request.email(), request.password());

        return tokenResponse(tokens);
    }

    @GetMapping("/csrf")
    public ResponseEntity<CsrfToken> csrf(CsrfToken csrfToken) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .body(csrfToken);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        AuthTokens tokens = authService.refresh(refreshToken);

        return tokenResponse(tokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        authService.logout(refreshToken);

        ResponseCookie expiredCookie = ResponseCookie
                .from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/auth")
                .maxAge(Duration.ZERO)
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, expiredCookie.toString())
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .build();
    }

    private ResponseEntity<Void> tokenResponse(AuthTokens tokens) {
        ResponseCookie responseCookie = ResponseCookie
                .from("refreshToken", tokens.refreshToken().value())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/auth")
                .maxAge(Duration.between(Instant.now(), tokens.refreshToken().expiresAt()))
                .build();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + tokens.accessToken().value()
                )
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .build();
    }
}
