// позволяет внешним клиентам получать статистику через HTTP-запросы

package com.bioengine.analytics.controller;

import com.bioengine.analytics.entity.ProcessedEvent;
import com.bioengine.analytics.repository.ProcessedEventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final ProcessedEventRepository repository;

    public AnalyticsController(ProcessedEventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/processed")
    public List<ProcessedEvent> processed() {
        return repository.findAll();
    }
}