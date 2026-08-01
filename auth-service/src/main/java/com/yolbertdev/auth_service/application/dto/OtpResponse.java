package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Schema(description = "Metadata of a generated OTP (the actual code is sent through the notification channel — never exposed here)")
@Getter
@Builder
@JsonPropertyOrder({"message", "purpose", "expiresAt"})
public class OtpResponse {

    @Schema(description = "Human-readable confirmation message", example = "OTP has been sent.")
    private String message;

    @Schema(description = "Purpose for which the OTP was generated", example = "EMAIL_VERIFICATION", allowableValues = {
            "EMAIL_VERIFICATION", "PASSWORD_RESET", "LOGIN_VERIFICATION"})
    private OtpPurpose purpose;

    @Schema(description = "Timestamp at which the OTP expires (ISO-8601 with offset)", example = "2026-07-29T18:00:00+00:00")
    private OffsetDateTime expiresAt;
}
