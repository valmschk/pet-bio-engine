//проверяет что сервис правильно обрабатывает данные, не запуская при этом реальную БД или Kafka

package com.bioengine.telemetry.service;

import com.bioengine.telemetry.dto.TelemetryRequestDto;
import com.bioengine.telemetry.entity.TelemetryRecord;
import com.bioengine.telemetry.repository.TelemetryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelemetryServiceTest {

    @Mock
    private TelemetryRepository repository;

    @Mock
    private KafkaTemplate<String, ?> kafkaTemplate;

    @InjectMocks
    private TelemetryService service;

    @Test
    void shouldProcessAndSaveTelemetry() {
        // 1. Подготовка
        TelemetryRequestDto request = new TelemetryRequestDto();
        request.setDeviceId("device-123");
        request.setMetricValue(42.0);

        TelemetryRecord expectedRecord = new TelemetryRecord();
        expectedRecord.setId(1L);
        expectedRecord.setEventId(UUID.randomUUID());
        expectedRecord.setDeviceId("device-123");
        expectedRecord.setMetricValue(42.0);

        when(repository.save(any(TelemetryRecord.class))).thenReturn(expectedRecord);

        TransactionSynchronizationManager.initSynchronization();
        
        try {
            TelemetryRecord actualResult = service.processAndSave(request);

            assertThat(actualResult).isNotNull();
            assertThat(actualResult.getId()).isEqualTo(1L);
            assertThat(actualResult.getDeviceId()).isEqualTo("device-123");

            verify(repository, times(1)).save(any(TelemetryRecord.class));
            
        } finally {
            TransactionSynchronizationManager.clearSynchronization();
        }
    }
}