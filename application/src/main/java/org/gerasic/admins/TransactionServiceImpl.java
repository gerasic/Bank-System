package org.gerasic.admins;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gerasic.contracts.admins.TransactionService;
import org.gerasic.contracts.admins.ValidateService;
import org.gerasic.dto.AccountEventDto;
import org.gerasic.mappers.TransactionMapper;
import org.gerasic.persistance.KafkaPublisher;
import org.gerasic.persistance.entities.AccountEntity;
import org.gerasic.persistance.entities.TransactionEntity;
import org.gerasic.persistance.repositories.AccountRepository;
import org.gerasic.persistance.repositories.TransactionRepository;
import org.gerasic.persistance.repositories.UserRepository;
import org.gerasic.persistance.repositories.specifications.TransactionSpecification;
import org.gerasic.transactions.TransactionType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ValidateService validator;
    private final KafkaPublisher kafkaPublisher;
    private final ObjectMapper objectMapper;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            UserRepository userRepository,
            ValidateService validator,
            KafkaPublisher kafkaPublisher,
            ObjectMapper objectMapper
    ) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.validator = validator;
        this.kafkaPublisher = kafkaPublisher;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        AccountEntity account = validator.validateAccountExists(accountId);
        account.setBalance(account.getBalance().add(amount));
        transactionRepository.save(createTransaction(account, amount, TransactionType.DEPOSIT));

        AccountEntity saved = accountRepository.save(account);
        kafkaPublisher.publishAccountEvent(saved.getId().toString(), createEvent(saved));
    }

    @Transactional
    @Override
    public void withdraw(Long accountId, BigDecimal amount) {
        AccountEntity account = validator.validateAccountExists(accountId);
        account.setBalance(account.getBalance().subtract(amount));
        transactionRepository.save(createTransaction(account, amount.negate(), TransactionType.WITHDRAW));
        AccountEntity saved = accountRepository.save(account);
        kafkaPublisher.publishAccountEvent(saved.getId().toString(), createEvent(saved));
    }

    @Transactional
    @Override
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        AccountEntity from = validator.validateAccountExists(fromAccountId);
        AccountEntity to   = validator.validateAccountExists(toAccountId);

        BigDecimal commission = calculateCommission(from.getOwnerLogin(), to.getOwnerLogin(), amount);
        BigDecimal total      = amount.add(commission);
        validator.validateSufficientFunds(fromAccountId, total);

        from.setBalance(from.getBalance().subtract(total));
        to.setBalance(to.getBalance().add(amount));

        transactionRepository.save(createTransaction(from, total.negate(), TransactionType.TRANSFER));
        transactionRepository.save(createTransaction(to, amount, TransactionType.TRANSFER));

        AccountEntity savedFrom = accountRepository.save(from);
        AccountEntity savedTo   = accountRepository.save(to);

        kafkaPublisher.publishAccountEvent(savedFrom.getId().toString(), createEvent(savedFrom));
        kafkaPublisher.publishAccountEvent(savedTo.getId().toString(), createEvent(savedTo));
    }

    @Override
    public List<org.gerasic.transactions.Transaction> getAllTransactions(
            Long accountId, BigDecimal amount, TransactionType type
    ) {
        Specification<TransactionEntity> spec = Specification
                .where(TransactionSpecification.hasAccountId(accountId))
                .and(TransactionSpecification.hasAmount(amount))
                .and(TransactionSpecification.hasTransactionType(type));

        return transactionRepository.findAll(spec).stream()
                .map(TransactionMapper::toModel)
                .toList();
    }

    private BigDecimal calculateCommission(String fromUser, String toUser, BigDecimal amount) {
        if (fromUser.equals(toUser)) {
            return BigDecimal.ZERO;
        }
        Optional<org.gerasic.persistance.entities.UserEntity> from =
                userRepository.findByLogin(fromUser);

        boolean isFriend = from
                .map(u -> u.getFriendLogins().contains(toUser))
                .orElse(false);

        return amount.multiply(
                isFriend
                        ? BigDecimal.valueOf(0.03)
                        : BigDecimal.valueOf(0.10)
        );
    }

    private TransactionEntity createTransaction(
            AccountEntity account, BigDecimal amount, TransactionType type
    ) {
        TransactionEntity tx = new TransactionEntity();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setType(type);
        return tx;
    }

    private AccountEventDto createEvent(AccountEntity account) {
        var payload = new org.gerasic.dto.AccountPayload(
                account.getOwnerLogin(),
                account.getBalance()
        );
        return new AccountEventDto(
                account.getId().toString(),
                "UPDATED",
                Instant.now(),
                payload
        );
    }
}
