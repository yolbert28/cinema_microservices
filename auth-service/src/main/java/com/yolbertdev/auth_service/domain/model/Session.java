package com.yolbertdev.auth_service.domain.model;

import com.yolbertdev.auth_service.domain.enums.DeviceOs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    private UUID id;
    private UUID userId;
    private String refreshTokenHash;
    private String deviceId;
    private DeviceOs deviceOs;
    private String userAgent;
    private String ipAddress;
    private boolean revoked;
    private OffsetDateTime revokedAt;
    private OffsetDateTime lastUsedAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private OffsetDateTime expiresAt;

    public static Session create(UUID userId,
            String refreshTokenHash,
            String deviceId,
            DeviceOs deviceOs,
            String userAgent,
            String ipAddress,
            int expirationDays) {
        OffsetDateTime now = OffsetDateTime.now();
        return Session.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .refreshTokenHash(refreshTokenHash)
                .deviceId(deviceId)
                .deviceOs(deviceOs)
                .userAgent(userAgent)
                .ipAddress(ipAddress)
                .revoked(false)
                .lastUsedAt(now)
                .createdAt(now)
                .updatedAt(now)
                .expiresAt(now.plusDays(expirationDays))
                .build();
    }

    public boolean isValid() {
        return !revoked && expiresAt.isAfter(OffsetDateTime.now());
    }

    public void revoke() {
        OffsetDateTime now = OffsetDateTime.now();
        this.revoked = true;
        this.revokedAt = now;
        this.updatedAt = now;
    }

    public void touch() {
        OffsetDateTime now = OffsetDateTime.now();
        this.lastUsedAt = now;
        this.updatedAt = now;
    }
}
