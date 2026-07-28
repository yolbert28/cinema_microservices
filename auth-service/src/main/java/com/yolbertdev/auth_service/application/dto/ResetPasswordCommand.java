package com.yolbertdev.auth_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordCommand {

    @NotNull
    private UUID userId;

    @NotBlank
    @Pattern(regexp = "^\\d{6}$", message = "OTP code must be exactly 6 digits")
    private String otpCode;

    @NotBlank
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String newPassword;
}
