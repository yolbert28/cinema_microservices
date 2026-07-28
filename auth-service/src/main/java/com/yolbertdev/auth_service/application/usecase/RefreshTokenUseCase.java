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

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public AuthTokenResponse execute(RefreshTokenCommand command) {
        String tokenHash = tokenProvider.hashRefreshToken(command.getRefreshToken());

        Session session = sessionRepository.findActiveByRefreshTokenHash(tokenHash)
                .orElseThrow(InvalidRefreshTokenException::new);

        if (!session.isValid()) {
            throw new InvalidRefreshTokenException();
        }

        User user = userRepository.findById(session.getUserId())
                .orElseThrow(() -> new UserNotFoundException(session.getUserId()));

        session.touch();
        sessionRepository.save(session);

        String newAccessToken = tokenProvider.generateAccessToken(user.getId(), user.getEmail(), user.getRole());

        return AuthTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(command.getRefreshToken())
                .accessTokenExpiresIn(tokenProvider.getAccessTokenExpiresIn())
                .build();
    }
}
