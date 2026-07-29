package com.yolbertdev.auth_service.application.usecase;

import com.yolbertdev.auth_service.application.dto.OtpResponse;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenerateOtpUseCase {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;

    @Value("${auth.otp.expiration-minutes:10}")
    private int expirationMinutes;

    /**
     * Generates an OTP for a user identified by their email address.
     * Used by public endpoints where the caller does not know the user's UUID.
     */
    @Transactional
    public OtpResponse execute(String email, OtpPurpose purpose) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return execute(user.getId(), purpose);
    }

    /**
     * Generates an OTP for a user identified by their UUID.
     * Used internally (e.g. post-registration flow).
     */
    @Transactional
    public OtpResponse execute(UUID userId, OtpPurpose purpose) {

        otpRepository.cancelActiveByUserIdAndPurpose(userId, purpose);

        String code = generateCode();

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

        Otp saved = otpRepository.save(otp);

        return OtpResponse.builder()
                .userId(saved.getUserId())
                .purpose(saved.getPurpose())
                .expiresAt(saved.getExpiresAt())
                .build();
    }

    private String generateCode() {
        return String.format("%06d", new SecureRandom().nextInt(1_000_000));
    }
}
