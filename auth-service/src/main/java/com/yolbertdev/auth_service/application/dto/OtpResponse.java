package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Metadata of a generated OTP (the actual code is sent through the notification channel — never exposed here)")
@Getter
@Builder
@JsonPropertyOrder({"userId", "purpose", "expiresAt"})
public class OtpResponse {

    @Schema(description = "UUID of the user to whom the OTP was issued", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID userId;

    @Schema(description = "Purpose for which the OTP was generated", example = "EMAIL_VERIFICATION",
            allowableValues = {"EMAIL_VERIFICATION", "PASSWORD_RESET", "LOGIN_VERIFICATION"})
    private OtpPurpose purpose;

    @Schema(description = "Timestamp at which the OTP expires (ISO-8601 with offset)", example = "2026-07-29T18:00:00+00:00")
    private OffsetDateTime expiresAt;
}
