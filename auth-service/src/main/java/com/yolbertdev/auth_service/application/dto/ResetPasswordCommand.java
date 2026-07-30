package com.yolbertdev.auth_service.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Payload to complete the password-reset flow using a valid OTP")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordCommand {

    @Schema(description = "Registered email address of the user resetting the password", example = "john.doe@example.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "6-digit OTP code received via email (PASSWORD_RESET purpose)", example = "391047")
    @NotBlank
    @Pattern(regexp = "^\\d{6}$", message = "OTP code must be exactly 6 digits")
    private String otpCode;

    @Schema(description = "New password — 8 to 30 characters", example = "NewSecur3P@ss!", minLength = 8, maxLength = 30)
    @NotBlank
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String newPassword;
}
