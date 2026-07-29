package com.yolbertdev.auth_service.application.exception;

public class EmailAlreadyTakenException extends AuthServiceException {

    public EmailAlreadyTakenException() {
        super("Email already registered");
    }
}
