package com.yolbertdev.auth_service.web.docs;

import com.yolbertdev.auth_service.application.dto.AuthTokenResponse;
import com.yolbertdev.auth_service.application.dto.LoginCommand;
import com.yolbertdev.auth_service.application.dto.RefreshTokenCommand;
import com.yolbertdev.auth_service.application.dto.RegisterUserCommand;
import com.yolbertdev.auth_service.web.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * OpenAPI contract for the Authentication endpoints.
 *
 * <p>All {@code @Operation}, {@code @ApiResponses} and {@code @SecurityRequirement}
 * annotations live here, keeping {@link com.yolbertdev.auth_service.web.controller.AuthController}
 * free of documentation noise.
 */
@Tag(name = "Authentication", description = "User registration, login, token refresh and logout")
public interface AuthApi {

    // ── POST /api/auth/register ───────────────────────────────────────────────

    @Operation(
            summary = "Register a new user",
            description = """
                    Creates a new user account with status `PENDING`.
                    After registration, a 6-digit OTP is sent to the provided email
                    for address verification (`EMAIL_VERIFICATION` purpose).
                    A `UserRegistered` domain event is published to the outbox table.
                    """
    )
    @SecurityRequirements()
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully registered and OTP sent"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload (validation failed)",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Email address is already registered",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> register(@Valid @RequestBody RegisterUserCommand command);

    // ── POST /api/auth/login ──────────────────────────────────────────────────

    @Operation(
            summary = "Authenticate a user",
            description = """
                    Validates credentials and creates a new device session, returning a
                    short-lived **access token** (default 15 min) and a long-lived
                    **refresh token** (default 7 days).

                    Failed login attempts are tracked. After `MAX_FAILED_ATTEMPTS`, the
                    account is locked temporarily to prevent brute force attacks.
                    """
    )
    @SecurityRequirements()
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful, tokens generated",
                    content = @Content(schema = @Schema(implementation = AuthTokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Account is not active (pending email verification or blocked)",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "429", description = "Account temporarily locked due to too many failed attempts",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<AuthTokenResponse> login(@Valid @RequestBody LoginCommand command);

    // ── POST /api/auth/refresh ────────────────────────────────────────────────

    @Operation(
            summary = "Refresh the access token",
            description = """
                    Exchanges a valid, non-revoked **refresh token** for a new access token.
                    The session's `last_used_at` is updated on every successful refresh.
                    If a refresh token that has already been consumed is presented, the
                    session is immediately revoked (stolen-token mitigation).
                    """
    )
    @SecurityRequirements()
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "New access token issued",
                    content = @Content(schema = @Schema(implementation = AuthTokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Refresh token is invalid, revoked or expired",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<AuthTokenResponse> refresh(@Valid @RequestBody RefreshTokenCommand command);

    // ── POST /api/auth/logout ─────────────────────────────────────────────────

    @Operation(
            summary = "Log out of one or all sessions",
            description = """
                    Revokes device sessions for the authenticated user.

                    | Parameters | Behaviour |
                    |---|---|
                    | `sessionId` provided | Revokes only that specific session |
                    | `all=true` | Revokes **all** active sessions for the user |
                    | Neither provided | Defaults to revoking all sessions |

                    **Requires** a valid JWT access token in the `Authorization` header.
                    """,
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Session(s) revoked successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Session not found for the given sessionId",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> logout(
            UUID userId,
            @RequestParam(required = false) UUID sessionId,
            @RequestParam(defaultValue = "false") boolean all);
}
