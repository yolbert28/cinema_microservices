package com.yolbertdev.auth_service.application.exception;

public class UserNotFoundException extends AuthServiceException {

    public UserNotFoundException() {
        super("User not found");
    }
}
