package org.gerasic.storage.consumer;

import org.gerasic.storage.dto.ClientEventDto;
import org.gerasic.storage.service.EventStorageService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ClientEventListener {

    private final EventStorageService storageService;

    public ClientEventListener(EventStorageService storageService) {
        this.storageService = storageService;
    }

    @KafkaListener(
            topics = "client-topic",
            containerFactory = "clientKafkaListenerContainerFactory"
    )
    public void onClientEvent(ClientEventDto dto) {
        storageService.saveClientEvent(dto);
    }
}
