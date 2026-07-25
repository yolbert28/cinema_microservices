CREATE TABLE session
(
    id                 UUID PRIMARY KEY,
    user_id            UUID        NOT NULL REFERENCES users (id),
    refresh_token_hash VARCHAR     NOT NULL,
    device_id          VARCHAR,
    device_os          VARCHAR     NOT NULL CHECK (device_os IN ('ANDROID', 'IOS', 'WEB', 'OTHER')),
    user_agent         VARCHAR,
    ip_address         INET,
    revoked            BOOLEAN     NOT NULL DEFAULT FALSE,
    revoked_at         TIMESTAMPTZ,
    last_used_at       TIMESTAMPTZ,
    created_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    expires_at         TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_session_user_id ON session (user_id);
CREATE INDEX idx_session_refresh_token_hash ON session (refresh_token_hash);
CREATE INDEX idx_session_active_token ON session (refresh_token_hash) WHERE revoked = FALSE;