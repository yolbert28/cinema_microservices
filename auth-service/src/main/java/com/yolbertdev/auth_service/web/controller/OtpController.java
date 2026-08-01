package com.yolbertdev.auth_service.web.controller;

import com.yolbertdev.auth_service.application.dto.OtpResponse;
import com.yolbertdev.auth_service.application.dto.ResetPasswordCommand;
import com.yolbertdev.auth_service.application.dto.ValidateOtpCommand;
import com.yolbertdev.auth_service.application.usecase.RequestPasswordResetUseCase;
import com.yolbertdev.auth_service.application.usecase.ResendOtpUseCase;
import com.yolbertdev.auth_service.application.usecase.ResetPasswordUseCase;
import com.yolbertdev.auth_service.application.usecase.ValidateOtpUseCase;
import com.yolbertdev.auth_service.web.docs.OtpApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OtpController implements OtpApi {

    private final ValidateOtpUseCase validateOtpUseCase;
    private final ResendOtpUseCase resendOtpUseCase;
    private final RequestPasswordResetUseCase requestPasswordResetUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    @PostMapping("/otp/validate")
    public ResponseEntity<Void> validateOtp(@Valid @RequestBody ValidateOtpCommand command) {
        validateOtpUseCase.execute(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/otp/resend")
    public ResponseEntity<Void> resendOtp(@Valid @RequestBody ResendOtpRequest request) {
        resendOtpUseCase.execute(request.email(), request.purpose());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/reset-request")
    public ResponseEntity<OtpResponse> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        return ResponseEntity.ok(requestPasswordResetUseCase.execute(request.email()));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordCommand command) {
        resetPasswordUseCase.execute(command);
        return ResponseEntity.ok().build();
    }
}
