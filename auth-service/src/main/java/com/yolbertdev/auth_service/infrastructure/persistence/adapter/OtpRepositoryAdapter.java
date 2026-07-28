package com.yolbertdev.auth_service.infrastructure.persistence.adapter;

import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.yolbertdev.auth_service.domain.enums.OtpStatus;
import com.yolbertdev.auth_service.domain.model.Otp;
import com.yolbertdev.auth_service.domain.repository.OtpRepository;
import com.yolbertdev.auth_service.infrastructure.persistence.jpa.JpaOtpRepository;
import com.yolbertdev.auth_service.infrastructure.persistence.mapper.OtpMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OtpRepositoryAdapter implements OtpRepository {

    private final JpaOtpRepository jpa;

    @Override
    public Optional<Otp> findActiveByUserIdAndPurpose(UUID userId, OtpPurpose purpose) {
        return jpa.findFirstActiveByUserIdAndPurpose(userId, purpose, OtpStatus.ACTIVE)
                .map(OtpMapper::toDomain);
    }

    @Override
    public void cancelActiveByUserIdAndPurpose(UUID userId, OtpPurpose purpose) {
        jpa.updateStatusByUserIdAndPurpose(userId, purpose, OtpStatus.ACTIVE, OtpStatus.CANCELLED);
    }

    @Override
    public Otp save(Otp otp) {
        return OtpMapper.toDomain(jpa.save(OtpMapper.toEntity(otp)));
    }
}
