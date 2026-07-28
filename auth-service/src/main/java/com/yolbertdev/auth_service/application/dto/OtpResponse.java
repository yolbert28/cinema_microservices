package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class OtpResponse {

    private UUID userId;
    private OtpPurpose purpose;
    private OffsetDateTime expiresAt;
}
