error id: file:///C:/bio-engine/analytics-service/src/main/java/com/bioengine/analytics/config/KafkaListenerConfig.java:_empty_/Configuration#
file:///C:/bio-engine/analytics-service/src/main/java/com/bioengine/analytics/config/KafkaListenerConfig.java
empty definition using pc, found symbol in pc: _empty_/Configuration#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 716
uri: file:///C:/bio-engine/analytics-service/src/main/java/com/bioengine/analytics/config/KafkaListenerConfig.java
text:
```scala
package com.bioengine.analytics.config;

import com.bioengine.common.dto.TelemetryEventDto;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Confi@@guration
public class KafkaListenerConfig {

    @Bean
    public DefaultErrorHandler errorHandler(KafkaOperations<String, TelemetryEventDto> template) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
                template,
                (record, ex) -> new TopicPartition(record.topic() + ".DLQ", record.partition())
        );

        FixedBackOff backOff = new FixedBackOff(1000L, 3L);
        DefaultErrorHandler handler = new DefaultErrorHandler(recoverer, backOff);

        handler.addNotRetryableExceptions(
                org.springframework.kafka.support.serializer.DeserializationException.class,
                com.fasterxml.jackson.core.JsonProcessingException.class
        );
        return handler;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TelemetryEventDto> kafkaListenerContainerFactory(
            ConsumerFactory<String, TelemetryEventDto> consumerFactory, // Берем бин из ConsumerConfig
            DefaultErrorHandler errorHandler) {
        
        ConcurrentKafkaListenerContainerFactory<String, TelemetryEventDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        
        factory.setConsumerFactory(consumerFactory); // Привязываем вручную
        factory.setCommonErrorHandler(errorHandler);
        factory.setConcurrency(3);

        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("kafka-vt-");
        executor.setVirtualThreads(true);
        factory.getContainerProperties().setListenerTaskExecutor(executor);

        return factory;
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/Configuration#