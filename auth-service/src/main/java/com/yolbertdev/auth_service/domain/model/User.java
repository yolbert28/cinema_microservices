package com.yolbertdev.auth_service.domain.model;

import com.yolbertdev.auth_service.domain.enums.UserRole;
import com.yolbertdev.auth_service.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private UserRole role;
    private UserStatus status;
    private int failedLoginAttempts;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime emailVerifiedAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public boolean isLocked() {
        return lockedUntil != null && lockedUntil.isAfter(OffsetDateTime.now());
    }

    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }

    public boolean isPending() {
        return status == UserStatus.PENDING;
    }

    public void recordFailedLogin(int maxAttempts, int lockoutMinutes) {
        this.failedLoginAttempts++;
        if (this.failedLoginAttempts >= maxAttempts) {
            this.lockedUntil = OffsetDateTime.now().plusMinutes(lockoutMinutes);
            this.failedLoginAttempts = 0;
        }
        touch();
    }

    public void recordSuccessfulLogin() {
        this.failedLoginAttempts = 0;
        this.lockedUntil = null;
        touch();
    }

    public void verifyEmail() {
        this.emailVerifiedAt = OffsetDateTime.now();
        this.status = UserStatus.ACTIVE;
        touch();
    }

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
        touch();
    }

    private void touch() {
        this.updatedAt = OffsetDateTime.now();
    }
}
