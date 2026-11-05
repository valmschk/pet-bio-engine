error id: file:///C:/bio-engine/telemetry-service/src/main/java/com/bioengine/telemetry/dto/TelemetryRequestDto.java:java/lang/String#
file:///C:/bio-engine/telemetry-service/src/main/java/com/bioengine/telemetry/dto/TelemetryRequestDto.java
empty definition using pc, found symbol in pc: java/lang/String#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 285
uri: file:///C:/bio-engine/telemetry-service/src/main/java/com/bioengine/telemetry/dto/TelemetryRequestDto.java
text:
```scala
package com.bioengine.telemetry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TelemetryRequestDto {
    @NotBlank
    private String deviceId;

    @NotNull
    private Double metricValue;

    public Str@@ing getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public Double getMetricValue() { return metricValue; }
    public void setMetricValue(Double metricValue) { this.metricValue = metricValue; }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: java/lang/String#