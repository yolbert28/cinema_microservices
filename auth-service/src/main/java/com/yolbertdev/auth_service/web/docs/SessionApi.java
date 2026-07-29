package com.yolbertdev.auth_service.web.docs;

import com.yolbertdev.auth_service.application.dto.SessionResponse;
import com.yolbertdev.auth_service.web.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

/**
 * OpenAPI contract for Session management endpoints.
 *
 * <p>All Swagger annotations live here, keeping
 * {@link com.yolbertdev.auth_service.web.controller.SessionController} clean.
 * Every endpoint in this group requires a valid JWT access token.
 */
@Tag(name = "Sessions", description = "List and revoke active device sessions")
@SecurityRequirement(name = "BearerAuth")
public interface SessionApi {

    // ── GET /api/auth/sessions ────────────────────────────────────────────────

    @Operation(
            summary = "List active sessions",
            description = """
                    Returns all non-revoked, non-expired sessions for the authenticated user.
                    Each entry represents a different device or browser that is currently
                    logged in.  Use the session `id` to revoke a specific device.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of active sessions",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SessionResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<List<SessionResponse>> listSessions(UUID userId);

    // ── DELETE /api/auth/sessions/{id} ────────────────────────────────────────

    @Operation(
            summary = "Revoke a specific session",
            description = """
                    Marks the given session as revoked (`revoked = true`).
                    Subsequent refresh-token requests from the corresponding device will be
                    rejected.  Only the owner of the session can revoke it.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Session revoked successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Session not found or does not belong to the authenticated user",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> revokeSession(
            UUID userId,
            @Parameter(description = "UUID of the session to revoke", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id);

    // ── DELETE /api/auth/sessions ─────────────────────────────────────────────

    @Operation(
            summary = "Revoke all sessions",
            description = """
                    Revokes **every** active session for the authenticated user across all
                    devices.  This is equivalent to a global sign-out.  The current access
                    token remains valid until it expires (tokens are stateless), but no
                    refresh will be possible from any device.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "All sessions revoked successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> revokeAllSessions(UUID userId);
}
