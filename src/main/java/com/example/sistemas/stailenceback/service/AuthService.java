package com.example.sistemas.stailenceback.service;

import com.example.sistemas.stailenceback.dto.auth.AuthenticationResponse;
import com.example.sistemas.stailenceback.dto.auth.LoginRequest;
import com.example.sistemas.stailenceback.dto.auth.RegisterRequest;
import com.example.sistemas.stailenceback.domain.entities.RefreshToken;
import com.example.sistemas.stailenceback.domain.entities.Usuario;
import com.example.sistemas.stailenceback.repository.RefreshTokenRepository;
import com.example.sistemas.stailenceback.repository.UsuarioRepository;
import com.example.sistemas.stailenceback.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenRepository refreshTokenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public AuthenticationResponse register(RegisterRequest req) {
        if (usuarioRepository.existsByCorreo(req.getCorreo())) {
            throw new RuntimeException("Correo ya registrado");
        }

        Usuario u = Usuario.builder()
                .uuid(UUID.randomUUID().toString())
                .correo(req.getCorreo())
                .hashContrasena(passwordEncoder.encode(req.getPassword()))
                .nombres(req.getNombres())
                .apellidos(req.getApellidos())
                .build();

        usuarioRepository.save(u);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", u.getId());

        String access = jwtService.generateAccessToken(u.getCorreo(), claims);
        String refresh = jwtService.generateRefreshToken(u.getCorreo());

        // persist refresh token
        RefreshToken rt = RefreshToken.builder()
                .token(refresh)
                .usuario(u)
                .expiresAt(LocalDateTime.now().plusSeconds(jwtServiceRefreshMs()))
                .build();
        refreshTokenRepository.save(rt);

        return new AuthenticationResponse(access, refresh, "Bearer", 86400L);
    }

    private long jwtServiceRefreshMs() {
        // leer por reflexión el valor desde JwtService no expuesto; usar 7 días por defecto
        return 604800;
    }

    public AuthenticationResponse login(LoginRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getCorreo(), req.getPassword()));
        Usuario u = usuarioRepository.findByCorreo(req.getCorreo()).orElseThrow();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", u.getId());

        String access = jwtService.generateAccessToken(u.getCorreo(), claims);
        String refresh = jwtService.generateRefreshToken(u.getCorreo());

        // persist refresh token
        RefreshToken rt = RefreshToken.builder()
                .token(refresh)
                .usuario(u)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();
        refreshTokenRepository.save(rt);

        return new AuthenticationResponse(access, refresh, "Bearer", 86400L);
    }

    @Transactional
    public AuthenticationResponse refreshToken(String refreshToken) {
        var opt = refreshTokenRepository.findByToken(refreshToken);
        if (opt.isEmpty()) throw new RuntimeException("Refresh token inválido");
        RefreshToken rt = opt.get();
        if (rt.getExpiresAt().isBefore(LocalDateTime.now()) || Boolean.TRUE.equals(rt.getRevoked())) {
            throw new RuntimeException("Refresh token expirado o revocado");
        }

        Usuario u = rt.getUsuario();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", u.getId());

        String newAccess = jwtService.generateAccessToken(u.getCorreo(), claims);
        String newRefresh = jwtService.generateRefreshToken(u.getCorreo());

        // revoke old token and save new one
        rt.setRevoked(true);
        refreshTokenRepository.save(rt);

        RefreshToken newRt = RefreshToken.builder()
                .token(newRefresh)
                .usuario(u)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();
        refreshTokenRepository.save(newRt);

        return new AuthenticationResponse(newAccess, newRefresh, "Bearer", 86400L);
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).ifPresent(rt -> {
            rt.setRevoked(true);
            refreshTokenRepository.save(rt);
        });
    }
}
