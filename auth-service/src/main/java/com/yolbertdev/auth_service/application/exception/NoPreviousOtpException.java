package com.yolbertdev.auth_service.application.exception;

public class NoPreviousOtpException extends RuntimeException {
    public NoPreviousOtpException() {
        super("No recent OTP found for this purpose. Please initiate the process again");
    }
}
