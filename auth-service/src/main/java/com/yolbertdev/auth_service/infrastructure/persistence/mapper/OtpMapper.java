package com.yolbertdev.auth_service.infrastructure.persistence.mapper;

import com.yolbertdev.auth_service.domain.model.Otp;
import com.yolbertdev.auth_service.infrastructure.persistence.entity.OtpEntity;

public final class OtpMapper {

    private OtpMapper() {}

    public static Otp toDomain(OtpEntity e) {
        return Otp.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .code(e.getCode())
                .purpose(e.getPurpose())
                .attempts(e.getAttempts())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .expiresAt(e.getExpiresAt())
                .build();
    }

    public static OtpEntity toEntity(Otp o) {
        return OtpEntity.builder()
                .id(o.getId())
                .userId(o.getUserId())
                .code(o.getCode())
                .purpose(o.getPurpose())
                .attempts(o.getAttempts())
                .status(o.getStatus())
                .createdAt(o.getCreatedAt())
                .expiresAt(o.getExpiresAt())
                .build();
    }
}
