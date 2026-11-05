//принимает HTTP-запросы через API Gateway и передает их в TelemetryService

package com.bioengine.telemetry.controller;

import com.bioengine.telemetry.dto.TelemetryRequestDto;
import com.bioengine.telemetry.entity.TelemetryRecord;
import com.bioengine.telemetry.service.TelemetryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {
    private final TelemetryService service;

    public TelemetryController(TelemetryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TelemetryRecord> createTelemetry(@Valid @RequestBody TelemetryRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.processAndSave(dto));
    }
}