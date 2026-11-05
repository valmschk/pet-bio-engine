package com.bioengine.telemetry.service;

import com.bioengine.common.dto.TelemetryEventDto;
import com.bioengine.telemetry.dto.TelemetryRequestDto;
import com.bioengine.telemetry.entity.TelemetryRecord;
import com.bioengine.telemetry.repository.TelemetryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class TelemetryService {

    private static final Logger log = LoggerFactory.getLogger(TelemetryService.class);
    private static final String TOPIC = "telemetry.events";

    private final TelemetryRepository repository;
    private final KafkaTemplate<String, TelemetryEventDto> kafkaTemplate;

    public TelemetryService(TelemetryRepository repository,
                            KafkaTemplate<String, TelemetryEventDto> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public TelemetryRecord processAndSave(TelemetryRequestDto dto) {
        TelemetryRecord record = new TelemetryRecord();
        record.setDeviceId(dto.getDeviceId());
        record.setMetricValue(dto.getMetricValue());
        TelemetryRecord saved = repository.save(record);

        // Асинхронная отправка после коммита — не блокируем поток и не шлём в Kafka при откате
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                TelemetryEventDto event = new TelemetryEventDto(
                        saved.getEventId(),
                        saved.getDeviceId(),
                        saved.getMetricValue(),
                        saved.getTimestamp()
                );
                kafkaTemplate.send(TOPIC, saved.getDeviceId(), event)
                        .whenComplete((result, ex) -> {
                            if (ex != null) {
                                log.error("Failed to publish telemetry event eventId={}", saved.getEventId(), ex);
                            } else {
                                log.debug("Published eventId={} to partition {}",
                                        saved.getEventId(),
                                        result.getRecordMetadata().partition());
                            }
                        });
            }
        });

        return saved;
    }
}