package com.yolbertdev.auth_service.infrastructure.persistence.mapper;

import com.yolbertdev.auth_service.domain.model.User;
import com.yolbertdev.auth_service.infrastructure.persistence.entity.UserEntity;

public final class UserMapper {

    private UserMapper() {}

    public static User toDomain(UserEntity e) {
        return User.builder()
                .id(e.getId())
                .name(e.getName())
                .lastname(e.getLastname())
                .email(e.getEmail())
                .password(e.getPassword())
                .role(e.getRole())
                .status(e.getStatus())
                .failedLoginAttempts(e.getFailedLoginAttempts())
                .lockedUntil(e.getLockedUntil())
                .emailVerifiedAt(e.getEmailVerifiedAt())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    public static UserEntity toEntity(User u) {
        return UserEntity.builder()
                .id(u.getId())
                .name(u.getName())
                .lastname(u.getLastname())
                .email(u.getEmail())
                .password(u.getPassword())
                .role(u.getRole())
                .status(u.getStatus())
                .failedLoginAttempts(u.getFailedLoginAttempts())
                .lockedUntil(u.getLockedUntil())
                .emailVerifiedAt(u.getEmailVerifiedAt())
                .createdAt(u.getCreatedAt())
                .updatedAt(u.getUpdatedAt())
                .build();
    }
}
