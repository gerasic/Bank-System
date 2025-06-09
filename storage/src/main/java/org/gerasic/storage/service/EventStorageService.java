package org.gerasic.storage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gerasic.storage.dto.*;
import org.gerasic.storage.entity.*;
import org.gerasic.storage.repository.*;
import org.springframework.stereotype.Service;

@Service
public class EventStorageService {
    private final ClientEventRepository clientRepo;
    private final AccountEventRepository accountRepo;
    private final ObjectMapper objectMapper;

    public EventStorageService(ClientEventRepository clientRepo,
                               AccountEventRepository accountRepo,
                               ObjectMapper objectMapper) {
        this.clientRepo = clientRepo;
        this.accountRepo = accountRepo;
        this.objectMapper = objectMapper;
    }

    public void saveClientEvent(ClientEventDto dto) {
        ClientEventEntity e = new ClientEventEntity();
        e.setClientId(dto.clientId());
        e.setAction(dto.action());
        e.setTimestamp(dto.timestamp());
        try {
            String json = objectMapper.writeValueAsString(dto.payload());
            e.setPayload(json);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Не удалось сериализовать payload", ex);
        }
        clientRepo.save(e);
    }

    public void saveAccountEvent(AccountEventDto dto) {
        AccountEventEntity e = new AccountEventEntity();
        e.setAccountId(dto.accountId());
        e.setAction(dto.action());
        e.setTimestamp(dto.timestamp());
        try {
            String json = objectMapper.writeValueAsString(dto.payload());
            e.setPayload(json);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Не удалось сериализовать payload", ex);
        }
        accountRepo.save(e);
    }
}
