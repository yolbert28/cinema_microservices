package com.yolbertdev.auth_service.web.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class ApiError {

    private int status;
    private String error;
    private String message;
    private OffsetDateTime timestamp;

    public static ApiError of(int status, String error, String message) {
        return ApiError.builder()
                .status(status)
                .error(error)
                .message(message)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}
