package com.example.sistemas.stailenceback.controller;

import com.example.sistemas.stailenceback.dto.auth.AuthenticationResponse;
import com.example.sistemas.stailenceback.dto.auth.LoginRequest;
import com.example.sistemas.stailenceback.dto.auth.RefreshRequest;
import com.example.sistemas.stailenceback.dto.auth.RegisterRequest;
import com.example.sistemas.stailenceback.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Registro de usuario")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @Operation(summary = "Login de usuario")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @Operation(summary = "Refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@Valid @RequestBody RefreshRequest req) {
        return ResponseEntity.ok(authService.refreshToken(req.getRefreshToken()));
    }

    @Operation(summary = "Logout (revocar refresh token)")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshRequest req) {
        authService.logout(req.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

}
