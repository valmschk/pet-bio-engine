CREATE TABLE processed_events (
    id BIGSERIAL PRIMARY KEY,
    event_id UUID NOT NULL,
    processed_at TIMESTAMP NOT NULL,
    CONSTRAINT uk_processed_event_id UNIQUE (event_id)
);