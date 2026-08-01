package com.yolbertdev.auth_service.application.usecase;

import com.yolbertdev.auth_service.application.dto.ValidateOtpCommand;
import com.yolbertdev.auth_service.application.exception.InvalidOtpException;
import com.yolbertdev.auth_service.application.exception.OtpMaxAttemptsExceededException;
import com.yolbertdev.auth_service.application.exception.UserNotFoundException;
import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.yolbertdev.auth_service.domain.model.Otp;
import com.yolbertdev.auth_service.domain.repository.OtpRepository;
import com.yolbertdev.auth_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ValidateOtpUseCase {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;

    @Value("${auth.otp.max-attempts:5}")
    private int maxAttempts;

    @Transactional
    public void execute(ValidateOtpCommand command) {

        if (command.getPurpose() == OtpPurpose.PASSWORD_RESET) {
            throw new InvalidOtpException("This endpoint is intended for 2FA validation only. Use the password-reset endpoint to validate PASSWORD_RESET codes");
        }

        UUID userId = userRepository.findByEmail(command.getEmail())
                .orElseThrow(UserNotFoundException::new)
                .getId();

        Otp otp = otpRepository
                .findActiveByUserIdAndPurpose(userId,command.getPurpose())
                .orElseThrow(InvalidOtpException::new);

        if (otp.isExpired()) {
            throw new InvalidOtpException();
        }

        if (!Objects.equals(otp.getCode(), command.getCode())) {
            boolean cancelled = otp.registerFailedAttempt(maxAttempts);
            otpRepository.save(otp);
            if (cancelled) {
                throw new OtpMaxAttemptsExceededException();
            }
            throw new InvalidOtpException();
        }

        otp.markAsUsed();
        otpRepository.save(otp);

        if (command.getPurpose() == OtpPurpose.EMAIL_VERIFICATION) {
            userRepository.findById(userId).ifPresent(user -> {
                user.verifyEmail();
                userRepository.save(user);
            });
        }
    }
}
