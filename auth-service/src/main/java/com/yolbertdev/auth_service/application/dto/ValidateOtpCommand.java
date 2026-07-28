package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateOtpCommand {

    @NotNull
    private UUID userId;

    @NotNull
    private OtpPurpose purpose;

    @NotBlank
    @Pattern(regexp = "^\\d{6}$", message = "OTP code must be exactly 6 digits")
    private String code;
}
