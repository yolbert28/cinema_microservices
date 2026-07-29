package com.yolbertdev.auth_service.web.docs;

import com.yolbertdev.auth_service.application.dto.OtpResponse;
import com.yolbertdev.auth_service.application.dto.ResetPasswordCommand;
import com.yolbertdev.auth_service.application.dto.ValidateOtpCommand;
import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.yolbertdev.auth_service.web.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * OpenAPI contract for OTP and password-reset endpoints.
 *
 * <p>Annotations are intentionally kept here so that
 * {@link com.yolbertdev.auth_service.web.controller.OtpController} stays readable.
 *
 * <p>The request body records ({@link ResendOtpRequest} and {@link PasswordResetRequest})
 * are defined here as canonical types so that both the interface and the controller
 * implementation share the exact same type signature.
 */
@Tag(name = "OTP", description = "One-time password validation, resend and password reset flows")
public interface OtpApi {

    // ── POST /api/auth/otp/validate ───────────────────────────────────────────

    @Operation(
            summary = "Validate a one-time password",
            description = """
                    Verifies the 6-digit OTP code for a given user (identified by **email**)
                    and purpose:

                    | Purpose | Effect on success |
                    |---|---|
                    | `EMAIL_VERIFICATION` | Sets `email_verified_at`; activates the account |
                    | `PASSWORD_RESET` | Authorises the subsequent `/password/reset` call |
                    | `LOGIN_VERIFICATION` | Completes the 2FA step after a successful login |

                    **This endpoint is public** — no JWT required.
                    **Attempt tracking**: each wrong code increments the attempt counter.
                    After `OTP_MAX_ATTEMPTS` failures the OTP is cancelled and a new one must
                    be requested via `/otp/resend`.  Expired OTPs are also rejected.
                    """
    )
    @SecurityRequirements()
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OTP is valid — action applied"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "No account found for the given email",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "422", description = "OTP is wrong, expired or cancelled",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> validateOtp(@Valid @RequestBody ValidateOtpCommand command);

    // ── POST /api/auth/otp/resend ─────────────────────────────────────────────

    @Operation(
            summary = "Resend / regenerate an OTP",
            description = """
                    Generates a fresh 6-digit OTP for the given user and the
                    requested purpose.  Any previously `ACTIVE` OTP for that same user
                    and purpose is cancelled to avoid ambiguity.

                    **This endpoint is public** — no JWT token required.  It is meant
                    to be used:
                    - Before the first login, to re-send the `EMAIL_VERIFICATION` code.
                    - During the password-reset flow, to re-send a `PASSWORD_RESET` code.
                    """
    )
    @SecurityRequirements()
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "New OTP generated — sent via the configured channel",
                    content = @Content(schema = @Schema(implementation = OtpResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid fields",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<OtpResponse> resendOtp(@Valid @RequestBody ResendOtpRequest request);

    // ── POST /api/auth/password/reset-request ─────────────────────────────────

    @Operation(
            summary = "Request a password-reset OTP",
            description = """
                    Initiates the password-reset flow for a given email address.
                    A 6-digit OTP with purpose `PASSWORD_RESET` is generated and dispatched
                    to the email via the notification microservice.

                    > **Security note**: the response is always `200 OK` regardless of
                    > whether the email exists in the system, to avoid user enumeration.
                    """
    )
    @SecurityRequirements()
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request processed — if the email exists an OTP has been sent"),
            @ApiResponse(responseCode = "400", description = "Email address is missing or malformed",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request);

    // ── POST /api/auth/password/reset ─────────────────────────────────────────

    @Operation(
            summary = "Reset the user password",
            description = """
                    Completes the password-reset flow.  Validates the `PASSWORD_RESET` OTP
                    and, if correct, replaces the user's password with the supplied value
                    (bcrypt-hashed before storage).

                    The OTP is marked `USED` after a successful reset and cannot be reused.
                    """
    )
    @SecurityRequirements()
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "422", description = "OTP is wrong, expired or cancelled",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordCommand command);

    // ── Shared request records ────────────────────────────────────────────────

    /**
     * Request body for {@code POST /api/auth/otp/resend}.
     * Defined here so the controller implementation shares the exact same type.
     */
    record ResendOtpRequest(
            @Schema(description = "Email address of the account requesting a new OTP", example = "john.doe@example.com")
            @NotBlank @Email String email,

            @Schema(
                    description = "The purpose for which to generate a new OTP",
                    example = "EMAIL_VERIFICATION",
                    allowableValues = {"EMAIL_VERIFICATION", "PASSWORD_RESET", "LOGIN_VERIFICATION"}
            )
            @NotNull OtpPurpose purpose
    ) {}

    /**
     * Request body for {@code POST /api/auth/password/reset-request}.
     * Defined here so the controller implementation shares the exact same type.
     */
    record PasswordResetRequest(
            @Schema(description = "Email address of the account to reset", example = "john.doe@example.com")
            @NotBlank @Email String email
    ) {}
}
