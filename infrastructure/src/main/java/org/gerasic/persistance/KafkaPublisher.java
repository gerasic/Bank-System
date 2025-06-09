package org.gerasic.persistance;

import org.gerasic.dto.AccountEventDto;
import org.gerasic.dto.ClientEventDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KafkaPublisher {
    private final KafkaTemplate<String, Object> kafka;

    public KafkaPublisher(KafkaTemplate<String, Object> kafka) {
        this.kafka = kafka;
    }

    public void publishAccountEvent(String id, AccountEventDto event) {
        kafka.send("account-topic", id,  event);
    };
    public void publishClientEvent(String id, ClientEventDto event) {
        kafka.send("client-topic", id, event);
    };
}
