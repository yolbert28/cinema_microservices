package com.yolbertdev.auth_service.application.usecase;

import com.yolbertdev.auth_service.application.dto.AuthTokenResponse;
import com.yolbertdev.auth_service.application.dto.LoginCommand;
import com.yolbertdev.auth_service.application.exception.AccountLockedException;
import com.yolbertdev.auth_service.application.exception.AccountNotActiveException;
import com.yolbertdev.auth_service.application.exception.InvalidCredentialsException;
import com.yolbertdev.auth_service.application.port.PasswordEncoderPort;
import com.yolbertdev.auth_service.application.port.TokenProvider;
import com.yolbertdev.auth_service.domain.model.OutboxEvent;
import com.yolbertdev.auth_service.domain.model.Session;
import com.yolbertdev.auth_service.domain.model.User;
import com.yolbertdev.auth_service.domain.repository.OutboxRepository;
import com.yolbertdev.auth_service.domain.repository.SessionRepository;
import com.yolbertdev.auth_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final OutboxRepository outboxRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenProvider tokenProvider;

    @Value("${auth.max-login-attempts:5}")
    private int maxLoginAttempts;

    @Value("${auth.lockout-duration-minutes:5}")
    private int lockoutDurationMinutes;

    @Value("${jwt.refresh-expiration-days:7}")
    private int refreshExpirationDays;

    @Transactional
    public AuthTokenResponse execute(LoginCommand command) {
        User user = userRepository.findByEmail(command.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isActive()) {
            throw new AccountNotActiveException();
        }

        if (user.isLocked()) {
            throw new AccountLockedException(user.getLockedUntil());
        }

        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            user.recordFailedLogin(maxLoginAttempts, lockoutDurationMinutes);
            userRepository.save(user);
            throw new InvalidCredentialsException();
        }

        user.recordSuccessfulLogin();
        userRepository.save(user);

        String accessToken = tokenProvider.generateAccessToken(user.getId(), user.getEmail(), user.getRole());

        UUID sessionId = UUID.randomUUID();
        String refreshToken = tokenProvider.generateRefreshToken(sessionId);
        String jti = tokenProvider.extractJti(refreshToken);

        Session session = Session.create(
                sessionId,
                user.getId(),
                jti,
                command.getDeviceId(),
                command.getDeviceOs(),
                command.getUserAgent(),
                command.getIpAddress(),
                refreshExpirationDays);

        sessionRepository.save(session);

        outboxRepository.save(OutboxEvent.create(
                user.getId(),
                "SESSION",
                "USER_LOGGED_IN",
                Map.of(
                        "userId", user.getId().toString(),
                        "sessionId", session.getId().toString(),
                        "deviceOs", command.getDeviceOs().name())));

        return AuthTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(tokenProvider.getAccessTokenExpiresIn())
                .build();
    }
}
