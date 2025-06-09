package org.gerasic.admins;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gerasic.accounts.Account;
import org.gerasic.contracts.admins.AccountService;
import org.gerasic.contracts.admins.ValidateService;
import org.gerasic.dto.AccountEventDto;
import org.gerasic.dto.AccountPayload;
import org.gerasic.mappers.AccountMapper;
import org.gerasic.persistance.KafkaPublisher;
import org.gerasic.persistance.entities.AccountEntity;
import org.gerasic.persistance.entities.UserEntity;
import org.gerasic.persistance.repositories.AccountRepository;
import org.gerasic.persistance.repositories.UserRepository;
import org.gerasic.persistance.repositories.specifications.AccountSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ValidateService validator;
    private final KafkaPublisher kafkaPublisher;
    private final ObjectMapper objectMapper;
    public AccountServiceImpl(AccountRepository accountRepository,
                              UserRepository userRepository,
                              ValidateService validator, KafkaPublisher kafkaPublisher,
                              ObjectMapper objectMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.validator = validator;
        this.kafkaPublisher = kafkaPublisher;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @Override
    public void createAccount(String login) {
        validator.validateUserExists(login);

        UserEntity user = userRepository.findByLogin(login).orElseThrow();
        AccountEntity account = new AccountEntity(login);

        AccountEntity saved = accountRepository.save(account);

        var event = createEvent(saved);
        kafkaPublisher.publishAccountEvent(saved.getId().toString(), event);
    }

    @Override
    public Account getAccountById(Long id) {
        validator.validateAccountExists(id);
        AccountEntity accountEntity = accountRepository.findById(id).orElseThrow();

        return AccountMapper.toModel(accountEntity);
    }

    @Override
    public List<Account> getUserAccounts(String login) {
        validator.validateUserExists(login);

        return accountRepository.findAllByOwnerLogin(login).stream()
                .map(AccountMapper::toModel)
                .toList();
    }

    @Override
    public List<Account> getAllAccounts(String ownerLogin) {
        Specification<AccountEntity> spec = Specification.where(AccountSpecification.hasOwnerLogin(ownerLogin));

        return accountRepository.findAll(spec).stream()
                .map(AccountMapper::toModel)
                .toList();
    }

    @Override
    public BigDecimal getBalance(Long accountId) {
        return validator.validateAccountExists(accountId).getBalance();
    }

    private AccountEventDto createEvent(AccountEntity saved) {
        AccountPayload payload = new AccountPayload(saved.getOwnerLogin(), saved.getBalance());

        return new AccountEventDto(
                saved.getId().toString(),
                "CREATED",
                Instant.now(),
                payload
        );
    }
}

