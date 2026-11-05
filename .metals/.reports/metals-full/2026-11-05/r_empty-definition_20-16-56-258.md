error id: file:///C:/bio-engine/analytics-service/src/main/java/com/bioengine/analytics/config/KafkaConsumerConfig.java:_empty_/JsonDeserializer#USE_TYPE_INFO_HEADERS#
file:///C:/bio-engine/analytics-service/src/main/java/com/bioengine/analytics/config/KafkaConsumerConfig.java
empty definition using pc, found symbol in pc: _empty_/JsonDeserializer#USE_TYPE_INFO_HEADERS#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 2157
uri: file:///C:/bio-engine/analytics-service/src/main/java/com/bioengine/analytics/config/KafkaConsumerConfig.java
text:
```scala
//фабрика консьюмеров
package com.bioengine.analytics.config;

import com.bioengine.common.dto.TelemetryEventDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset:earliest}")
    private String autoOffsetReset;

    @Bean
    public ConsumerFactory<String, TelemetryEventDto> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); //ручной коммит оффсетов
        
       
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        
        //настраиваю парсер +(защита от уязвимостей)
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.bioengine.common.dto");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.bioengine.common.dto.TelemetryEventDto");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEAD@@ERS, false);

        return new DefaultKafkaConsumerFactory<>(config);
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/JsonDeserializer#USE_TYPE_INFO_HEADERS#