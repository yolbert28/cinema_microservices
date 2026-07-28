package com.yolbertdev.auth_service.application.exception;

import java.time.OffsetDateTime;

public class AccountLockedException extends AuthServiceException {

    private final OffsetDateTime lockedUntil;

    public AccountLockedException(OffsetDateTime lockedUntil) {
        super("Account is temporarily locked until " + lockedUntil);
        this.lockedUntil = lockedUntil;
    }

    public OffsetDateTime getLockedUntil() {
        return lockedUntil;
    }
}
