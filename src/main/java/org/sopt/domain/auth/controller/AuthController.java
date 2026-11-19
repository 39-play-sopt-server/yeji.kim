package org.sopt.domain.auth.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.sopt.domain.auth.dto.response.AuthTokensResponse;
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

    public record SignupRequest(
            @NotBlank String name,
            @NotBlank @Email String email,
            @NotBlank String password
    ) {
    }

    public record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String password
    ) {
    }

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(
           @Valid @RequestBody SignupRequest request
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
            @Valid @RequestBody LoginRequest request
    ) {
        AuthTokensResponse tokens = authService.login(request.email(), request.password());

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
        AuthTokensResponse tokens = authService.refresh(refreshToken);

        return tokenResponse(tokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader(
                    name = HttpHeaders.AUTHORIZATION,
                    required = false
            ) String authorization,
            @CookieValue(
                    name = "refreshToken",
                    required = false
            ) String refreshToken
    ) {
        String accessToken = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            accessToken = authorization.substring(7);
        }

        authService.logout(accessToken, refreshToken);

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

    private ResponseEntity<Void> tokenResponse(AuthTokensResponse tokens) {
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
