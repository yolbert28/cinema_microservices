package com.yolbertdev.auth_service.application.port;

import com.yolbertdev.auth_service.domain.enums.UserRole;

import java.util.UUID;

public interface TokenProvider {

    String generateAccessToken(UUID userId, String email, UserRole role);

    String generateRefreshToken();

    String hashRefreshToken(String refreshToken);

    UUID extractUserId(String accessToken);

    String extractEmail(String accessToken);

    String extractRole(String accessToken);

    boolean isAccessTokenValid(String accessToken);

    long getAccessTokenExpiresIn();
}
