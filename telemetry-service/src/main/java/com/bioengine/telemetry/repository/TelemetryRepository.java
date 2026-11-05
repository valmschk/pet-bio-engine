//слой доступа к данным для сущности TelemetryRecord

package com.bioengine.telemetry.repository;

import com.bioengine.telemetry.entity.TelemetryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TelemetryRepository extends JpaRepository<TelemetryRecord, Long> {
    boolean existsByEventId(UUID eventId);
}