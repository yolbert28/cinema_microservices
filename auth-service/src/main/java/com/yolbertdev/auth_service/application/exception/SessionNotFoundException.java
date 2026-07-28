package com.yolbertdev.auth_service.application.exception;

import java.util.UUID;

public class SessionNotFoundException extends AuthServiceException {

    public SessionNotFoundException(UUID sessionId) {
        super("Session not found: " + sessionId);
    }

    public SessionNotFoundException() {
        super("Session not found");
    }
}
