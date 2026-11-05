CREATE TABLE telemetry_records (
    id BIGSERIAL PRIMARY KEY,
    event_id UUID NOT NULL UNIQUE,
    device_id VARCHAR(255) NOT NULL,
    metric_value DOUBLE PRECISION NOT NULL,
    timestamp TIMESTAMP NOT NULL
);

CREATE INDEX idx_telemetry_device ON telemetry_records (device_id);