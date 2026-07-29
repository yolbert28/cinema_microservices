CREATE TABLE session
(
    id                 UUID PRIMARY KEY,
    user_id            UUID        NOT NULL REFERENCES users (id),
    jti                VARCHAR     NOT NULL UNIQUE,
    device_id          VARCHAR,
    device_os          VARCHAR     NOT NULL CHECK (device_os IN ('ANDROID', 'IOS', 'WEB', 'OTHER')),
    user_agent         VARCHAR,
    ip_address         VARCHAR,
    revoked            BOOLEAN     NOT NULL DEFAULT FALSE,
    revoked_at         TIMESTAMPTZ,
    last_used_at       TIMESTAMPTZ,
    created_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    expires_at         TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_session_user_id ON session (user_id);
CREATE INDEX idx_session_jti ON session (jti);
CREATE INDEX idx_session_active_token ON session (jti) WHERE revoked = FALSE;