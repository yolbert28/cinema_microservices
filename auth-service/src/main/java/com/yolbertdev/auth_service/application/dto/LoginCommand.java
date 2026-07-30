package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.DeviceOs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Credentials and device information required to authenticate a user")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCommand {

        @Schema(description = "Registered email address", example = "john.doe@example.com")
        @NotBlank
        @Email
        private String email;

        @Schema(description = "Account password — 8 to 30 characters", example = "Secur3P@ss!", minLength = 8, maxLength = 30)
        @NotBlank
        @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
        private String password;

        @Schema(description = "Unique identifier for the device making the request. Should persist across logins on the same device.", example = "device-uuid-android-abc123")
        @NotBlank
        private String deviceId;

        @Schema(description = "Operating system of the device", example = "ANDROID", allowableValues = { "ANDROID",
                        "IOS", "WEB", "OTHER" })
        @NotNull
        private DeviceOs deviceOs;

        @Schema(description = "User-Agent header from the HTTP client (optional)", example = "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36")
        private String userAgent;

        @Schema(description = "IP address of the originating request (optional — can be resolved server-side)", example = "203.0.113.42")
        private String ipAddress;
}
