//какие именно данные клиент должен прислать в теле HTTP-запроса

package com.bioengine.telemetry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TelemetryRequestDto {
    @NotBlank
    private String deviceId;

    @NotNull
    private Double metricValue;

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public Double getMetricValue() { return metricValue; }
    public void setMetricValue(Double metricValue) { this.metricValue = metricValue; }
}