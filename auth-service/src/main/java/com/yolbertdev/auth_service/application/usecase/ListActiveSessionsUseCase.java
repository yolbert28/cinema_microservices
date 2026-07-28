package com.yolbertdev.auth_service.application.usecase;

import com.yolbertdev.auth_service.application.dto.SessionResponse;
import com.yolbertdev.auth_service.domain.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListActiveSessionsUseCase {

    private final SessionRepository sessionRepository;

    @Transactional(readOnly = true)
    public List<SessionResponse> execute(UUID userId) {
        return sessionRepository.findActiveByUserId(userId).stream()
                .map(session -> SessionResponse.builder()
                        .id(session.getId())
                        .deviceOs(session.getDeviceOs())
                        .deviceId(session.getDeviceId())
                        .userAgent(session.getUserAgent())
                        .ipAddress(session.getIpAddress())
                        .lastUsedAt(session.getLastUsedAt())
                        .createdAt(session.getCreatedAt())
                        .expiresAt(session.getExpiresAt())
                        .build())
                .toList();
    }
}
