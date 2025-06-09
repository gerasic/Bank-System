package org.gerasic.storage.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.gerasic.storage.dto.AccountEventDto;
import org.gerasic.storage.dto.ClientEventDto;
import org.springframework.context.annotation.*;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.*;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.*;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private Map<String, Object> commonProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "storage-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, ClientEventDto> clientConsumerFactory() {
        JsonDeserializer<ClientEventDto> deserializer =
                new JsonDeserializer<>(ClientEventDto.class)
                        .trustedPackages("org.gerasic.storage.dto")
                        .ignoreTypeHeaders();
        return new DefaultKafkaConsumerFactory<>(
                commonProps(),
                new StringDeserializer(),
                deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClientEventDto>
    clientKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, ClientEventDto>();
        factory.setConsumerFactory(clientConsumerFactory());
        factory.setConcurrency(3);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, AccountEventDto> accountConsumerFactory() {
        JsonDeserializer<AccountEventDto> deserializer =
                new JsonDeserializer<>(AccountEventDto.class)
                        .trustedPackages("org.gerasic.storage.dto")
                        .ignoreTypeHeaders();
        return new DefaultKafkaConsumerFactory<>(
                commonProps(),
                new StringDeserializer(),
                deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountEventDto>
    accountKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, AccountEventDto>();
        factory.setConsumerFactory(accountConsumerFactory());
        factory.setConcurrency(3);
        return factory;
    }
}
