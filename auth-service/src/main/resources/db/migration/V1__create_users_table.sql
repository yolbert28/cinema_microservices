CREATE TABLE users
(
    id                    UUID PRIMARY KEY,
    name                  VARCHAR     NOT NULL,
    lastname              VARCHAR     NOT NULL,
    email                 VARCHAR     NOT NULL,
    password              TEXT        NOT NULL,
    role                  VARCHAR     NOT NULL CHECK (role IN ('CLIENT', 'WORKER', 'ADMIN')),
    status                VARCHAR     NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE', 'BLOCKED')),
    failed_login_attempts INT         NOT NULL DEFAULT 0,
    locked_until          TIMESTAMPTZ,
    email_verified_at     TIMESTAMPTZ,
    created_at            TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at            TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX idx_users_email ON users (email);