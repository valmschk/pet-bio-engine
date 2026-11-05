package com.bioengine.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TelemetryEventDto {
    private UUID eventId;
    private String deviceId;
    private Double metricValue;
    private LocalDateTime timestamp;

    public TelemetryEventDto() {}

    public TelemetryEventDto(UUID eventId, String deviceId, Double metricValue, LocalDateTime timestamp) {
        this.eventId = eventId;
        this.deviceId = deviceId;
        this.metricValue = metricValue;
        this.timestamp = timestamp;
    }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public Double getMetricValue() { return metricValue; }
    public void setMetricValue(Double metricValue) { this.metricValue = metricValue; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}