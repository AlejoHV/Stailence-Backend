package com.example.sistemas.stailenceback.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${app.jwt.secret:change_me}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms:86400000}") // 24h default
    private long jwtExpirationMs;

    @Value("${app.jwt.refresh-expiration-ms:604800000}") // 7d default
    private long refreshExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String subject, Map<String, Object> claims, long expirationMs) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, jwtExpirationMs);
    }

    public String generateRefreshToken(String subject) {
        return generateToken(subject, Map.of(), refreshExpirationMs);
    }

    public Claims parseClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

}

