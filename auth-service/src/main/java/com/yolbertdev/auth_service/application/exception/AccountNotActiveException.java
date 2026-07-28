package com.yolbertdev.auth_service.application.exception;

public class AccountNotActiveException extends AuthServiceException {

    public AccountNotActiveException() {
        super("Account is not active. Please verify your email.");
    }
}
