//обработки ошибок и потоков для Kafka-консьюмера

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

@Configuration
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

        // использую виртуальные потоки для легковесной обработки
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("kafka-vt-");
        executor.setVirtualThreads(true);
        factory.getContainerProperties().setListenerTaskExecutor(executor);

        return factory;
    }
}