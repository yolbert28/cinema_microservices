package com.yolbertdev.auth_service.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthTokenResponse {

    private String accessToken;
    private String refreshToken;

    private long accessTokenExpiresIn;
}
