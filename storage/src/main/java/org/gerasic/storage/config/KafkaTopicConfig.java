package org.gerasic.storage.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic clientTopic() {
        return TopicBuilder.name("client-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic accountTopic() {
        return TopicBuilder.name("account-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
