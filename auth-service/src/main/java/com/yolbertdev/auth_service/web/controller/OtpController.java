package com.yolbertdev.auth_service.web.controller;

import com.yolbertdev.auth_service.application.dto.OtpResponse;
import com.yolbertdev.auth_service.application.dto.ResetPasswordCommand;
import com.yolbertdev.auth_service.application.dto.ValidateOtpCommand;
import com.yolbertdev.auth_service.application.usecase.GenerateOtpUseCase;
import com.yolbertdev.auth_service.application.usecase.RequestPasswordResetUseCase;
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
    private final GenerateOtpUseCase generateOtpUseCase;
    private final RequestPasswordResetUseCase requestPasswordResetUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    @PostMapping("/otp/validate")
    public ResponseEntity<Void> validateOtp(@Valid @RequestBody ValidateOtpCommand command) {
        validateOtpUseCase.execute(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/otp/resend")
    public ResponseEntity<OtpResponse> resendOtp(@Valid @RequestBody ResendOtpRequest request) {
        OtpResponse response = generateOtpUseCase.execute(request.email(), request.purpose());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password/reset-request")
    public ResponseEntity<Void> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        requestPasswordResetUseCase.execute(request.email());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordCommand command) {
        resetPasswordUseCase.execute(command);
        return ResponseEntity.ok().build();
    }
}
