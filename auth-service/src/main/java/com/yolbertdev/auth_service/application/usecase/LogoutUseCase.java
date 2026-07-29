package com.yolbertdev.auth_service.application.usecase;

import com.yolbertdev.auth_service.application.exception.SessionNotFoundException;
import com.yolbertdev.auth_service.domain.model.Session;
import com.yolbertdev.auth_service.domain.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {

    private final SessionRepository sessionRepository;

    @Transactional
    public void execute(UUID userId, UUID sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(SessionNotFoundException::new);

        if (!Objects.equals(session.getUserId(), userId)) {
            throw new SessionNotFoundException();
        }

        session.revoke();
        sessionRepository.save(session);
    }

    @Transactional
    public void executeAll(UUID userId) {
        sessionRepository.revokeAllByUserId(userId);
    }
}
