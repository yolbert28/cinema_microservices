package com.yolbertdev.auth_service.infrastructure.security;

import com.yolbertdev.auth_service.application.port.TokenProvider;
import com.yolbertdev.auth_service.domain.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HexFormat;
import java.util.UUID;

@Component
public class JwtTokenProvider implements TokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms:900000}")
    private long expirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    @Override
    public String generateAccessToken(UUID userId, String email, UserRole role) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .claim("role", role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    @Value("${jwt.refresh-expiration-days:7}")
    private long refreshExpirationDays;

    @Override
    public String generateRefreshToken(UUID sessionId) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .claim("sessionId", sessionId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (refreshExpirationDays * 24L * 60L * 60L * 1000L)))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String extractJti(String token) {
        return parseClaims(token).getId();
    }

    @Override
    public UUID extractSessionId(String token) {
        String sessionIdStr = parseClaims(token).get("sessionId", String.class);
        if (sessionIdStr == null) {
            throw new JwtException("Session ID missing in refresh token");
        }
        return UUID.fromString(sessionIdStr);
    }

    @Override
    public UUID extractUserId(String token) {
        return UUID.fromString(parseClaims(token).getSubject());
    }

    @Override
    public String extractEmail(String token) {
        return parseClaims(token).get("email", String.class);
    }

    @Override
    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    @Override
    public boolean isAccessTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public long getAccessTokenExpiresIn() {
        return expirationMs;
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
