package com.yolbertdev.auth_service.application.exception;

public class OtpMaxAttemptsExceededException extends AuthServiceException {

    public OtpMaxAttemptsExceededException() {
        super("OTP has been cancelled due to too many failed attempts. Please request a new code.");
    }
}
