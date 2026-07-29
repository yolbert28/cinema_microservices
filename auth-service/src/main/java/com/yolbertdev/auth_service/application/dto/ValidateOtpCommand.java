package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Payload to validate a one-time password code")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateOtpCommand {

    @Schema(description = "Email address of the account to verify", example = "john.doe@example.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "Purpose of the OTP to validate", example = "EMAIL_VERIFICATION",
            allowableValues = {"EMAIL_VERIFICATION", "PASSWORD_RESET", "LOGIN_VERIFICATION"})
    @NotNull
    private OtpPurpose purpose;

    @Schema(description = "6-digit numeric OTP code sent to the user", example = "847291")
    @NotBlank
    @Pattern(regexp = "^\\d{6}$", message = "OTP code must be exactly 6 digits")
    private String code;
}
