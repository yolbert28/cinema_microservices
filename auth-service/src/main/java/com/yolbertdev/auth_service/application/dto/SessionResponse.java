package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.DeviceOs;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class SessionResponse {

    private UUID id;
    private DeviceOs deviceOs;
    private String deviceId;
    private String userAgent;
    private String ipAddress;
    private OffsetDateTime lastUsedAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime expiresAt;
}
