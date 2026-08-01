package com.yolbertdev.auth_service.web.exception;

import com.yolbertdev.auth_service.application.exception.AccountLockedException;
import com.yolbertdev.auth_service.application.exception.AccountNotActiveException;
import com.yolbertdev.auth_service.application.exception.EmailAlreadyTakenException;
import com.yolbertdev.auth_service.application.exception.InvalidCredentialsException;
import com.yolbertdev.auth_service.application.exception.InvalidOtpException;
import com.yolbertdev.auth_service.application.exception.InvalidRefreshTokenException;
import com.yolbertdev.auth_service.application.exception.NoPreviousOtpException;
import com.yolbertdev.auth_service.application.exception.OtpMaxAttemptsExceededException;
import com.yolbertdev.auth_service.application.exception.OtpRateLimitExceededException;
import com.yolbertdev.auth_service.application.exception.SessionNotFoundException;
import com.yolbertdev.auth_service.application.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyTaken(EmailAlreadyTakenException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiError.of(409, "Conflict", ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiError.of(401, "Unauthorized", ex.getMessage()));
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<ApiError> handleAccountNotActive(AccountNotActiveException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiError.of(403, "Forbidden", ex.getMessage()));
    }

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ApiError> handleAccountLocked(AccountLockedException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(ApiError.of(429, "Too Many Requests", ex.getMessage()));
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ApiError> handleInvalidOtp(InvalidOtpException ex) {
        return ResponseEntity.unprocessableContent()
                .body(ApiError.of(422, "Invalid OTP", ex.getMessage()));
    }

    @ExceptionHandler(OtpMaxAttemptsExceededException.class)
    public ResponseEntity<ApiError> handleOtpMaxAttempts(OtpMaxAttemptsExceededException ex) {
        return ResponseEntity.unprocessableContent()
                .body(ApiError.of(422, "OTP Cancelled", ex.getMessage()));
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ApiError> handleInvalidRefreshToken(InvalidRefreshTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiError.of(401, "Unauthorized", ex.getMessage()));
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<ApiError> handleSessionNotFound(SessionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiError.of(404, "Not Found", ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiError.of(404, "Not Found", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": "
                        + Objects.requireNonNullElse(fe.getDefaultMessage(),
                        "Validation error"))
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.of(400, "Bad Request", message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.of(400, "Bad Request", "Malformed JSON request or invalid enum value"));
    }

    @ExceptionHandler(NoPreviousOtpException.class)
    public ResponseEntity<ApiError> handleNoPreviousOtp(NoPreviousOtpException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ApiError.of(422, "Unprocessable Entity", ex.getMessage()));
    }

    @ExceptionHandler(OtpRateLimitExceededException.class)
    public ResponseEntity<ApiError> handleOtpRateLimitExceeded(OtpRateLimitExceededException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(ApiError.of(429, "Too Many Requests", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiError.of(500, "Internal Server Error", "An unexpected error occurred"));
    }
}
