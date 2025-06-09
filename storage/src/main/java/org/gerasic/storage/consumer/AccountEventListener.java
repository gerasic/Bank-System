package org.gerasic.storage.consumer;

import org.gerasic.storage.dto.AccountEventDto;
import org.gerasic.storage.service.EventStorageService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventListener {

    private final EventStorageService storageService;

    public AccountEventListener(EventStorageService storageService) {
        this.storageService = storageService;
    }

    @KafkaListener(
            topics = "account-topic",
            containerFactory = "accountKafkaListenerContainerFactory"
    )
    public void onAccountEvent(AccountEventDto dto) {
        storageService.saveAccountEvent(dto);
    }
}

