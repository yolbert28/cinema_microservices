package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.DeviceOs;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Active session information for a specific device")
@Getter
@Builder
@JsonPropertyOrder({"id", "deviceOs", "deviceId", "userAgent", "ipAddress", "lastUsedAt", "createdAt", "expiresAt"})
public class SessionResponse {

    @Schema(description = "Unique session identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Operating system of the device", example = "ANDROID",
            allowableValues = {"ANDROID", "IOS", "WEB", "OTHER"})
    private DeviceOs deviceOs;

    @Schema(description = "Client-generated device identifier — persists across logins on the same device",
            example = "device-uuid-android-abc123")
    private String deviceId;

    @Schema(description = "User-Agent string from the HTTP client", example = "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36")
    private String userAgent;

    @Schema(description = "IP address from which the session was created", example = "203.0.113.42")
    private String ipAddress;

    @Schema(description = "Timestamp of the last refresh-token use for this session (ISO-8601)", example = "2026-07-29T14:30:00+00:00")
    private OffsetDateTime lastUsedAt;

    @Schema(description = "Timestamp when this session was created (ISO-8601)", example = "2026-07-22T09:00:00+00:00")
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when this session naturally expires (ISO-8601)", example = "2026-08-05T09:00:00+00:00")
    private OffsetDateTime expiresAt;
}
