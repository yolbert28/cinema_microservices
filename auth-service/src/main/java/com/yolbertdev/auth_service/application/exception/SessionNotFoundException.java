package com.yolbertdev.auth_service.application.exception;


public class SessionNotFoundException extends AuthServiceException {

    public SessionNotFoundException() {
        super("Session not found");
    }
}
