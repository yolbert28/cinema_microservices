package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.DeviceOs;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCommand {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String password;

    @NotBlank
    private String deviceId;

    @NotNull
    private DeviceOs deviceOs;

    private String userAgent;

    private String ipAddress;
}
