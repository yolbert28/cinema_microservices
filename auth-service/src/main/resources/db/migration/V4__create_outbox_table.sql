CREATE TABLE outbox
(
    id             UUID PRIMARY KEY,
    aggregate_id   UUID        NOT NULL,
    aggregate_type VARCHAR     NOT NULL,
    event_type     VARCHAR     NOT NULL,
    payload        JSONB       NOT NULL,
    status         VARCHAR     NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'COMPLETE', 'FAIL')),
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_outbox_status ON outbox (status);
CREATE INDEX idx_outbox_aggregate ON outbox (aggregate_id, aggregate_type);