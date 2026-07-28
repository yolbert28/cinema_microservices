package com.yolbertdev.auth_service.domain.repository;

import com.yolbertdev.auth_service.domain.model.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {

    Optional<Session> findActiveByRefreshTokenHash(String refreshTokenHash);

    List<Session> findActiveByUserId(UUID userId);

    Optional<Session> findById(UUID id);

    Session save(Session session);

    void revokeAllByUserId(UUID userId);
}
