package com.bioengine.analytics.repository;

import com.bioengine.analytics.entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, Long> {
    // existsByEventId удалён: не используется (consumer полагается на уникальный индекс + INSERT)
}