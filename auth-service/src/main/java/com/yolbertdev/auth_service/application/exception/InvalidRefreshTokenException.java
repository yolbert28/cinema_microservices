package com.yolbertdev.auth_service.application.exception;

public class InvalidRefreshTokenException extends AuthServiceException {

    public InvalidRefreshTokenException() {
        super("Refresh token is invalid, revoked, or expired");
    }
}
