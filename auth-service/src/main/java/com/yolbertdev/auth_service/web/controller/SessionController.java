package com.yolbertdev.auth_service.web.controller;

import com.yolbertdev.auth_service.application.dto.SessionResponse;
import com.yolbertdev.auth_service.application.usecase.ListActiveSessionsUseCase;
import com.yolbertdev.auth_service.application.usecase.LogoutUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final ListActiveSessionsUseCase listActiveSessionsUseCase;
    private final LogoutUseCase logoutUseCase;

    @GetMapping
    public ResponseEntity<List<SessionResponse>> listSessions(@AuthenticationPrincipal UUID userId) {
        return ResponseEntity.ok(listActiveSessionsUseCase.execute(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> revokeSession(
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID id) {
        logoutUseCase.execute(userId, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> revokeAllSessions(@AuthenticationPrincipal UUID userId) {
        logoutUseCase.executeAll(userId);
        return ResponseEntity.noContent().build();
    }
}
