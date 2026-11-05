//слушатель сообщений из кафка

package com.bioengine.analytics.consumer;

import com.bioengine.common.dto.TelemetryEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsConsumer {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsConsumer.class);
    private final ProcessedEventWriter writer;

    public AnalyticsConsumer(ProcessedEventWriter writer) {
        this.writer = writer;
    }

    @KafkaListener(
            topics = "telemetry.events",
            groupId = "analytics-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(TelemetryEventDto event) {
        if (event == null || event.getEventId() == null) {
            log.warn("Skipping malformed event: {}", event);
            return;
        }

        log.info("Received telemetry event [eventId={}, deviceId={}, virtualThread={}]",
                event.getEventId(), event.getDeviceId(), Thread.currentThread().isVirtual());
        //проверка на дубликат
        boolean firstWriter = writer.markProcessed(event.getEventId());
        if (!firstWriter) {
            log.warn("Duplicate event detected, skipping: eventId={}", event.getEventId());
            return;
        }

        log.info("[ANALYTICS INSIGHT] Device: {} | Metric: {} | Timestamp: {} | VirtualThread: {}",
                event.getDeviceId(), event.getMetricValue(), event.getTimestamp(),
                Thread.currentThread().isVirtual());
        log.info("Successfully processed eventId={}", event.getEventId());
    }
}