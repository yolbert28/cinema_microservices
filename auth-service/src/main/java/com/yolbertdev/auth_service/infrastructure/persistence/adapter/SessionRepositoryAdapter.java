package com.yolbertdev.auth_service.infrastructure.persistence.adapter;

import com.yolbertdev.auth_service.domain.model.Session;
import com.yolbertdev.auth_service.domain.repository.SessionRepository;
import com.yolbertdev.auth_service.infrastructure.persistence.jpa.JpaSessionRepository;
import com.yolbertdev.auth_service.infrastructure.persistence.mapper.SessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SessionRepositoryAdapter implements SessionRepository {

    private final JpaSessionRepository jpa;

    @Override
    public Optional<Session> findActiveByRefreshTokenHash(String refreshTokenHash) {
        return jpa.findByRefreshTokenHashAndRevokedFalse(refreshTokenHash)
                .map(SessionMapper::toDomain);
    }

    @Override
    public List<Session> findActiveByUserId(UUID userId) {
        return jpa.findByUserIdAndRevokedFalse(userId).stream()
                .map(SessionMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Session> findById(UUID id) {
        return jpa.findById(id).map(SessionMapper::toDomain);
    }

    @Override
    public Session save(Session session) {
        return SessionMapper.toDomain(jpa.save(SessionMapper.toEntity(session)));
    }

    @Override
    public void revokeAllByUserId(UUID userId) {
        jpa.revokeAllByUserId(userId, OffsetDateTime.now());
    }
}
