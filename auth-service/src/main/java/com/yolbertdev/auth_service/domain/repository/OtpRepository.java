package com.yolbertdev.auth_service.domain.repository;

import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.yolbertdev.auth_service.domain.model.Otp;

import java.util.Optional;
import java.util.UUID;

public interface OtpRepository {

    Optional<Otp> findActiveByUserIdAndPurpose(UUID userId, OtpPurpose purpose);

    void cancelActiveByUserIdAndPurpose(UUID userId, OtpPurpose purpose);

    Otp save(Otp otp);

    Optional<Otp> findLatestByUserId(UUID userId);

    Optional<Otp> findLatestByUserIdAndPurpose(UUID userId, OtpPurpose purpose);

    long countByUserIdAndCreatedAtAfter(UUID userId, java.time.OffsetDateTime timestamp);
}
