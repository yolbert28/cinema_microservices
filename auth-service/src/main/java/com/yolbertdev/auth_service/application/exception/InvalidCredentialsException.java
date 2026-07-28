package com.yolbertdev.auth_service.application.exception;

public class InvalidCredentialsException extends AuthServiceException {

    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
}
