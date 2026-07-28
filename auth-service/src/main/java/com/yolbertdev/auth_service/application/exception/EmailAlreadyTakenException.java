package com.yolbertdev.auth_service.application.exception;

public class EmailAlreadyTakenException extends AuthServiceException {

    public EmailAlreadyTakenException(String email) {
        super("Email already registered: " + email);
    }
}
