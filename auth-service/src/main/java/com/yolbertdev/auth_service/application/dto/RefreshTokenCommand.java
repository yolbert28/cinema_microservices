package com.yolbertdev.auth_service.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Payload to exchange a refresh token for a new access token")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenCommand {

    @Schema(description = "The refresh token obtained during login", example = "dGhpcyBpcyBhIHJlZnJlc2ggdG9rZW4gZXhhbXBsZQ==")
    @NotBlank
    private String refreshToken;
}
