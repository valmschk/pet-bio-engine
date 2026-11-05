//гарантия At-least-once

package com.bioengine.analytics.consumer;

import com.bioengine.analytics.entity.ProcessedEvent;
import com.bioengine.analytics.repository.ProcessedEventRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class ProcessedEventWriter {
    private final ProcessedEventRepository repository;

    public ProcessedEventWriter(ProcessedEventRepository repository) {
        this.repository = repository;
    }
    //отдельная транзакция, чтобы ошибка дубликата не откатывала всю обработку
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean markProcessed(UUID eventId) {
        try {
            ProcessedEvent processed = new ProcessedEvent();
            processed.setEventId(eventId);
            repository.saveAndFlush(processed);
            return true;
        } catch (DataIntegrityViolationException ex) {
            return false;
        }
    }
}