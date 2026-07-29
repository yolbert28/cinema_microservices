package com.yolbertdev.auth_service.application.usecase;

import com.yolbertdev.auth_service.application.dto.RegisterUserCommand;
import com.yolbertdev.auth_service.application.exception.EmailAlreadyTakenException;
import com.yolbertdev.auth_service.application.port.PasswordEncoderPort;
import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.yolbertdev.auth_service.domain.enums.UserStatus;
import com.yolbertdev.auth_service.domain.model.OutboxEvent;
import com.yolbertdev.auth_service.domain.model.User;
import com.yolbertdev.auth_service.domain.repository.OutboxRepository;
import com.yolbertdev.auth_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final OutboxRepository outboxRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final GenerateOtpUseCase generateOtpUseCase;

    @Transactional
    public void execute(RegisterUserCommand command) {
        if (userRepository.findByEmail(command.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException();
        }

        OffsetDateTime now = OffsetDateTime.now();
        User user = User.builder()
                .id(UUID.randomUUID())
                .name(command.getName())
                .lastname(command.getLastname())
                .email(command.getEmail())
                .password(passwordEncoder.encode(command.getPassword()))
                .role(command.getRole())
                .status(UserStatus.PENDING)
                .failedLoginAttempts(0)
                .createdAt(now)
                .updatedAt(now)
                .build();

        userRepository.save(user);

        generateOtpUseCase.execute(user.getId(), OtpPurpose.EMAIL_VERIFICATION);

        outboxRepository.save(OutboxEvent.create(
                user.getId(),
                "USER",
                "USER_REGISTERED",
                Map.of(
                        "userId", user.getId().toString(),
                        "email", user.getEmail(),
                        "name", user.getName())));
    }
}
