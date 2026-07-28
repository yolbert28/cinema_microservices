package com.yolbertdev.auth_service.web.controller;

import com.yolbertdev.auth_service.application.dto.AuthTokenResponse;
import com.yolbertdev.auth_service.application.dto.LoginCommand;
import com.yolbertdev.auth_service.application.dto.RefreshTokenCommand;
import com.yolbertdev.auth_service.application.dto.RegisterUserCommand;
import com.yolbertdev.auth_service.application.usecase.LoginUseCase;
import com.yolbertdev.auth_service.application.usecase.LogoutUseCase;
import com.yolbertdev.auth_service.application.usecase.RefreshTokenUseCase;
import com.yolbertdev.auth_service.application.usecase.RegisterUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserCommand command) {
        registerUserUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@Valid @RequestBody LoginCommand command) {
        return ResponseEntity.ok(loginUseCase.execute(command));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokenResponse> refresh(@Valid @RequestBody RefreshTokenCommand command) {
        return ResponseEntity.ok(refreshTokenUseCase.execute(command));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @AuthenticationPrincipal UUID userId,
            @RequestParam(required = false) UUID sessionId,
            @RequestParam(defaultValue = "false") boolean all) {
        if (all) {
            logoutUseCase.executeAll(userId);
        } else if (sessionId != null) {
            logoutUseCase.execute(userId, sessionId);
        } else {
            logoutUseCase.executeAll(userId);
        }
        return ResponseEntity.noContent().build();
    }
}
