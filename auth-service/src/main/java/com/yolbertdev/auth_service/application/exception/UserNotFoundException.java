package com.yolbertdev.auth_service.application.exception;

import java.util.UUID;

public class UserNotFoundException extends AuthServiceException {

    public UserNotFoundException(UUID userId) {
        super("User not found: " + userId);
    }
}
