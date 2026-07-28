package com.yolbertdev.auth_service.domain.model;

import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.yolbertdev.auth_service.domain.enums.OtpStatus;
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
public class Otp {

    private UUID id;
    private UUID userId;
    private String code;
    private OtpPurpose purpose;
    private int attempts;
    private OtpStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime expiresAt;

    public boolean isExpired() {
        return expiresAt.isBefore(OffsetDateTime.now());
    }

    public boolean isUsable() {
        return status == OtpStatus.ACTIVE && !isExpired();
    }

    public boolean registerFailedAttempt(int maxAttempts) {
        this.attempts++;
        if (this.attempts >= maxAttempts) {
            this.status = OtpStatus.CANCELLED;
            return true;
        }
        return false;
    }

    public void markAsUsed() {
        this.status = OtpStatus.USED;
    }
}
