package com.yolbertdev.auth_service.application.exception;

public class InvalidOtpException extends AuthServiceException {

    public InvalidOtpException() {
        super("Invalid or expired OTP code");
    }
}
