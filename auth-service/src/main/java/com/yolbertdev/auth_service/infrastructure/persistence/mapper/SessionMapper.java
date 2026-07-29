package com.yolbertdev.auth_service.infrastructure.persistence.mapper;

import com.yolbertdev.auth_service.domain.model.Session;
import com.yolbertdev.auth_service.infrastructure.persistence.entity.SessionEntity;

public final class SessionMapper {

    private SessionMapper() {}

    public static Session toDomain(SessionEntity e) {
        return Session.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .jti(e.getJti())
                .deviceId(e.getDeviceId())
                .deviceOs(e.getDeviceOs())
                .userAgent(e.getUserAgent())
                .ipAddress(e.getIpAddress())
                .revoked(e.isRevoked())
                .revokedAt(e.getRevokedAt())
                .lastUsedAt(e.getLastUsedAt())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .expiresAt(e.getExpiresAt())
                .build();
    }

    public static SessionEntity toEntity(Session s) {
        return SessionEntity.builder()
                .id(s.getId())
                .userId(s.getUserId())
                .jti(s.getJti())
                .deviceId(s.getDeviceId())
                .deviceOs(s.getDeviceOs())
                .userAgent(s.getUserAgent())
                .ipAddress(s.getIpAddress())
                .revoked(s.isRevoked())
                .revokedAt(s.getRevokedAt())
                .lastUsedAt(s.getLastUsedAt())
                .createdAt(s.getCreatedAt())
                .updatedAt(s.getUpdatedAt())
                .expiresAt(s.getExpiresAt())
                .build();
    }
}
