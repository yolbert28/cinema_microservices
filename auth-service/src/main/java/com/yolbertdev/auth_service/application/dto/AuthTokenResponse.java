package com.yolbertdev.auth_service.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "JWT tokens returned after a successful login or token refresh")
@Getter
@Builder
public class AuthTokenResponse {

    @Schema(description = "Short-lived JWT access token — include in the Authorization header as Bearer <token>",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Long-lived refresh token — use it to obtain a new access token via POST /api/auth/refresh",
            example = "dGhpcyBpcyBhIHJlZnJlc2ggdG9rZW4gZXhhbXBsZQ==")
    private String refreshToken;

    @Schema(description = "Access token lifetime in milliseconds (default: 900000 = 15 min)", example = "900000")
    private long accessTokenExpiresIn;
}
