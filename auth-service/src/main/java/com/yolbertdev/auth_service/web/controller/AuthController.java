package com.yolbertdev.auth_service.web.controller;

import com.yolbertdev.auth_service.application.dto.*;
import com.yolbertdev.auth_service.application.usecase.*;
import com.yolbertdev.auth_service.web.docs.AuthApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserCommand command) {
        registerUserUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/user/{id}/role")
    public ResponseEntity<Void> updateRole(@PathVariable UUID id, @Valid @RequestBody UpdateRoleCommand command) {
        updateRoleUseCase.execute(id, command);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(getUserByEmailUseCase.execute(email));
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
