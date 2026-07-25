CREATE TABLE otp
(
    id         UUID PRIMARY KEY,
    user_id    UUID        NOT NULL REFERENCES users (id),
    code       VARCHAR     NOT NULL,
    purpose    VARCHAR     NOT NULL CHECK (purpose IN ('EMAIL_VERIFICATION', 'PASSWORD_RESET', 'LOGIN_VERIFICATION')),
    attempts   INT         NOT NULL DEFAULT 0,
    status     VARCHAR     NOT NULL CHECK (status IN ('ACTIVE', 'USED', 'EXPIRED', 'CANCELLED')),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    expires_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_otp_user_id ON otp (user_id);
CREATE INDEX idx_otp_user_purpose_status ON otp (user_id, purpose, status);