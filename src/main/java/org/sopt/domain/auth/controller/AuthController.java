package org.sopt.domain.auth.controller;

import org.sopt.domain.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public record SignupRequest(String email, String password) {}
    public record LoginRequest(String email, String password) {}
    public record LoginResponse(String accessToken) {}

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(
            @RequestBody SignupRequest request
    ) {
        return ResponseEntity.ok(authService.signup(request.email(), request.password()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        String accessToken = authService.login(request.email(), request.password());
        return ResponseEntity.ok(new LoginResponse(accessToken));
    }
}
