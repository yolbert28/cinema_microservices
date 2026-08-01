package com.yolbertdev.auth_service.application.usecase;

import com.yolbertdev.auth_service.application.exception.NoPreviousOtpException;
import com.yolbertdev.auth_service.application.exception.OtpRateLimitExceededException;
import com.yolbertdev.auth_service.application.exception.UserNotFoundException;
import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.yolbertdev.auth_service.domain.enums.OtpStatus;
import com.yolbertdev.auth_service.domain.model.Otp;
import com.yolbertdev.auth_service.domain.model.User;
import com.yolbertdev.auth_service.domain.repository.OtpRepository;
import com.yolbertdev.auth_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResendOtpUseCase {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;

    @Value("${auth.otp.expiration-minutes:10}")
    private int expirationMinutes;

    @Value("${auth.otp.cooldown-seconds:30}")
    private int cooldownSeconds;

    @Value("${auth.otp.max-per-hour:5}")
    private int maxPerHour;

    /**
     * Grace period (in minutes) after OTP expiration during which a resend is still allowed.
     * After this window, the user must restart the flow from scratch.
     */
    @Value("${auth.otp.resend-grace-minutes:5}")
    private int resendGraceMinutes;

    @Transactional
    public void execute(String email, OtpPurpose purpose) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return;
        }

        UUID userId = userOpt.get().getId();

        Otp previousOtp = otpRepository.findLatestByUserIdAndPurpose(userId, purpose)
                .orElseThrow(NoPreviousOtpException::new);

        OffsetDateTime resendDeadline = previousOtp.getExpiresAt().plusMinutes(resendGraceMinutes);
        if (OffsetDateTime.now().isAfter(resendDeadline)) {
            throw new NoPreviousOtpException();
        }

        OffsetDateTime cooldownTime = previousOtp.getCreatedAt().plusSeconds(cooldownSeconds);
        if (OffsetDateTime.now().isBefore(cooldownTime)) {
            throw new OtpRateLimitExceededException("Please wait before requesting another OTP");
        }

        long countInLastHour = otpRepository.countByUserIdAndCreatedAtAfter(userId, OffsetDateTime.now().minusHours(1));
        if (countInLastHour >= maxPerHour) {
            throw new OtpRateLimitExceededException("Too many OTP requests. Please try again later");
        }

        otpRepository.cancelActiveByUserIdAndPurpose(userId, purpose);

        String code = String.format("%06d", new SecureRandom().nextInt(1_000_000));

        Otp otp = Otp.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .code(code)
                .purpose(purpose)
                .attempts(0)
                .status(OtpStatus.ACTIVE)
                .createdAt(OffsetDateTime.now())
                .expiresAt(OffsetDateTime.now().plusMinutes(expirationMinutes))
                .build();

        otpRepository.save(otp);
    }
}
