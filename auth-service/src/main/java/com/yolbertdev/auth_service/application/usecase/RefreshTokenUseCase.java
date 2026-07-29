package com.yolbertdev.auth_service.application.usecase;

import com.yolbertdev.auth_service.application.dto.AuthTokenResponse;
import com.yolbertdev.auth_service.application.dto.RefreshTokenCommand;
import com.yolbertdev.auth_service.application.exception.InvalidRefreshTokenException;
import com.yolbertdev.auth_service.application.exception.UserNotFoundException;
import com.yolbertdev.auth_service.application.port.TokenProvider;
import com.yolbertdev.auth_service.domain.model.Session;
import com.yolbertdev.auth_service.domain.model.User;
import com.yolbertdev.auth_service.domain.repository.SessionRepository;
import com.yolbertdev.auth_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public AuthTokenResponse execute(RefreshTokenCommand command) {
        String rawToken = command.getRefreshToken();
        UUID sessionId;
        String jti;

        try {
            sessionId = tokenProvider.extractSessionId(rawToken);
            jti = tokenProvider.extractJti(rawToken);
        } catch (Exception e) {
            throw new InvalidRefreshTokenException();
        }

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(InvalidRefreshTokenException::new);

        if (!session.isValid()) {
            throw new InvalidRefreshTokenException();
        }

        if (!jti.equals(session.getJti())) {
            session.revoke();
            sessionRepository.save(session);
            throw new InvalidRefreshTokenException();
        }

        User user = userRepository.findById(session.getUserId())
                .orElseThrow(UserNotFoundException::new);

        String newRefreshToken = tokenProvider.generateRefreshToken(sessionId);
        String newJti = tokenProvider.extractJti(newRefreshToken);

        session.setJti(newJti);
        session.touch();
        sessionRepository.save(session);

        String newAccessToken = tokenProvider.generateAccessToken(user.getId(), user.getEmail(), user.getRole());

        return AuthTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .accessTokenExpiresIn(tokenProvider.getAccessTokenExpiresIn())
                .build();
    }
}
