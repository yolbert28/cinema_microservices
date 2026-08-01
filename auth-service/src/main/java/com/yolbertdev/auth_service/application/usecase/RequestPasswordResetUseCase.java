package com.yolbertdev.auth_service.application.usecase;

import com.yolbertdev.auth_service.application.dto.OtpResponse;
import com.yolbertdev.auth_service.application.exception.UserNotFoundException;
import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.yolbertdev.auth_service.domain.model.OutboxEvent;
import com.yolbertdev.auth_service.domain.model.User;
import com.yolbertdev.auth_service.domain.repository.OutboxRepository;
import com.yolbertdev.auth_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestPasswordResetUseCase {

    private final UserRepository userRepository;
    private final OutboxRepository outboxRepository;
    private final GenerateOtpUseCase generateOtpUseCase;

    @Transactional
    public OtpResponse execute(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        OtpResponse otpResponse = generateOtpUseCase.execute(user.getId(), OtpPurpose.PASSWORD_RESET);

        outboxRepository.save(OutboxEvent.create(
                user.getId(),
                "USER",
                "PASSWORD_RESET_REQUESTED",
                Map.of(
                        "userId", user.getId().toString(),
                        "email", user.getEmail(),
                        "expiresAt", otpResponse.getExpiresAt().toString())));

        return otpResponse;
    }
}
