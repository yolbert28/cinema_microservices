package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.UserRole;
import com.yolbertdev.auth_service.domain.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "User information details")
@Getter
@Builder
@JsonPropertyOrder({"id", "name", "lastname", "email", "role", "status", "createdAt"})
public class UserResponse {

    @Schema(description = "Unique user identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "User's first name", example = "John")
    private String name;

    @Schema(description = "User's last name", example = "Doe")
    private String lastname;

    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "User's role", example = "USER", allowableValues = {"USER", "ADMIN"})
    private UserRole role;

    @Schema(description = "User's current status", example = "ACTIVE", allowableValues = {"PENDING", "ACTIVE", "BLOCKED"})
    private UserStatus status;

    @Schema(description = "Timestamp when the user was created (ISO-8601)", example = "2026-07-22T09:00:00+00:00")
    private OffsetDateTime createdAt;
}
