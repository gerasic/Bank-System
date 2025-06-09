package org.gerasic.admins;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gerasic.accounts.Account;
import org.gerasic.contracts.admins.*;
import org.gerasic.dto.*;
import org.gerasic.mappers.*;
import org.gerasic.persistance.entities.*;
import org.gerasic.persistance.repositories.*;
import org.gerasic.persistance.repositories.specifications.*;
import org.gerasic.transactions.*;

import org.gerasic.users.Gender;
import org.gerasic.users.HairColor;
import org.gerasic.users.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ValidateService validator;
    private final KafkaTemplate<String, Object> kafka;
    private final ObjectMapper objectMapper;

    public AdminServiceImpl(UserRepository userRepository,
                            AccountRepository accountRepository,
                            TransactionRepository transactionRepository,
                            ValidateService validator,
                            KafkaTemplate<String, Object> kafka,
                            ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.validator = validator;
        this.kafka = kafka;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @Override
    public void createUser(String login, String name, int age, Gender gender, HairColor hairColor) {
        validator.validateUserNotExists(login);
        UserEntity saved = userRepository.save(
                UserMapper.toEntity(new User(login, name, age, gender, hairColor))
        );
        publishClientEvent(
                saved.getLogin(),
                "CREATED",
                new ClientPayload(saved.getLogin(), saved.getName(), saved.getAge(), saved.getGender(), saved.getHairColor())
        );
    }

    @Override
    public String getUserInfo(String login) {
        validator.validateUserExists(login);
        return userRepository.findByLogin(login)
                .map(UserMapper::toModel)
                .map(Object::toString)
                .orElseThrow();
    }

    @Transactional
    @Override
    public void deleteUser(String login) {
        UserEntity saved = validator.validateUserExists(login);
        userRepository.delete(saved);
        publishClientEvent(
                saved.getLogin(),
                "DELETED",
                new ClientPayload(
                        saved.getLogin(),
                        saved.getName(),
                        saved.getAge(),
                        saved.getGender(),
                        saved.getHairColor())
        );
    }

    @Transactional
    @Override
    public void addFriend(String login, String friendLogin) {
        validator.validateNotSameLogin(login, friendLogin);
        validator.validateUserExists(login);
        validator.validateUserExists(friendLogin);

        UserEntity user   = userRepository.findByLogin(login).orElseThrow();
        UserEntity friend = userRepository.findByLogin(friendLogin).orElseThrow();

        user.addFriend(friendLogin);
        friend.addFriend(login);
        userRepository.save(user);
        userRepository.save(friend);
    }

    @Transactional
    @Override
    public void removeFriend(String login, String friendLogin) {
        validator.validateNotSameLogin(login, friendLogin);
        validator.validateUserExists(login);
        validator.validateUserExists(friendLogin);

        UserEntity user   = userRepository.findByLogin(login).orElseThrow();
        UserEntity friend = userRepository.findByLogin(friendLogin).orElseThrow();

        user.removeFriend(friendLogin);
        friend.removeFriend(login);
        userRepository.save(user);
        userRepository.save(friend);
    }

    @Override
    public List<String> getFriends(String login) {
        return validator.validateUserExists(login)
                .getFriendLogins()
                .stream()
                .toList();
    }

    @Override
    public List<User> getAllUsers(String name, String gender, String hairColor, Integer age) {
        return userRepository.findAll(UserSpecification.hasName(name)
                        .and(UserSpecification.hasGender(gender))
                        .and(UserSpecification.hasHairColor(hairColor))
                        .and(UserSpecification.hasAge(age)))
                .stream()
                .map(UserMapper::toModel)
                .toList();
    }

    @Transactional
    @Override
    public void createAccount(String login) {
        validator.validateUserExists(login);
        AccountEntity saved = accountRepository.save(new AccountEntity(login));
        publishAccountEvent(
                saved.getId().toString(),
                "CREATED",
                new AccountPayload(saved.getOwnerLogin(), saved.getBalance())
        );
    }

    @Override
    public List<Account> getUserAccounts(String login) {
        validator.validateUserExists(login);
        return accountRepository.findAllByOwnerLogin(login)
                .stream()
                .map(AccountMapper::toModel)
                .toList();
    }

    @Override
    public List<Account> getAllAccounts(String ownerLogin) {
        return accountRepository.findAll(AccountSpecification.hasOwnerLogin(ownerLogin))
                .stream()
                .map(AccountMapper::toModel)
                .toList();
    }

    @Override
    public BigDecimal getBalance(Long accountId) {
        return validator.validateAccountExists(accountId).getBalance();
    }

    @Transactional
    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        AccountEntity account = validator.validateAccountExists(accountId);
        account.setBalance(account.getBalance().add(amount));
        transactionRepository.save(createTransaction(account, amount, TransactionType.DEPOSIT));
        AccountEntity saved = accountRepository.save(account);
        publishAccountEvent(
                saved.getId().toString(),
                "DEPOSIT",
                new AccountPayload(saved.getOwnerLogin(), saved.getBalance())
        );
    }

    @Transactional
    @Override
    public void withdraw(Long accountId, BigDecimal amount) {
        AccountEntity account = validator.validateAccountExists(accountId);
        account.setBalance(account.getBalance().subtract(amount));
        transactionRepository.save(createTransaction(account, amount, TransactionType.WITHDRAW));
        AccountEntity saved = accountRepository.save(account);
        publishAccountEvent(
                saved.getId().toString(),
                "WITHDRAW",
                new AccountPayload(saved.getOwnerLogin(), saved.getBalance())
        );
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
        to.setBalance(to.getBalance().add(total));
        transactionRepository.save(createTransaction(from, total.negate(), TransactionType.TRANSFER));
        transactionRepository.save(createTransaction(to, total,          TransactionType.TRANSFER));
        AccountEntity savedFrom = accountRepository.save(from);
        AccountEntity savedTo   = accountRepository.save(to);

        publishAccountEvent(
                savedFrom.getId().toString(),
                "TRANSFER_OUT",
                new AccountPayload(savedFrom.getOwnerLogin(), savedFrom.getBalance())
        );
        publishAccountEvent(
                savedTo.getId().toString(),
                "TRANSFER_IN",
                new AccountPayload(savedTo.getOwnerLogin(),   savedTo.getBalance())
        );
    }

    @Override
    public List<Transaction> getAllTransactions(Long accountId, BigDecimal amount, TransactionType type) {
        Specification<TransactionEntity> spec = Specification
                .where(TransactionSpecification.hasAccountId(accountId))
                .and(TransactionSpecification.hasAmount(amount))
                .and(TransactionSpecification.hasTransactionType(type));

        return transactionRepository.findAll(spec)
                .stream()
                .map(TransactionMapper::toModel)
                .toList();
    }

    private void publishClientEvent(String key, String action, ClientPayload payload) {
        ClientEventDto event = new ClientEventDto(key, action, Instant.now(), payload);
        kafka.send("client-topic", key, event);
    }

    private void publishAccountEvent(String key, String action, AccountPayload payload) {
        AccountEventDto event = new AccountEventDto(key, action, Instant.now(), payload);
        kafka.send("account-topic", key, event);
    }

    private TransactionEntity createTransaction(AccountEntity account, BigDecimal amount, TransactionType type) {
        TransactionEntity tx = new TransactionEntity();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setType(type);
        return tx;
    }

    private BigDecimal calculateCommission(String fromUser, String toUser, BigDecimal amount) {
        if (fromUser.equals(toUser)) return BigDecimal.ZERO;
        boolean isFriend = userRepository.findByLogin(fromUser)
                .map(u -> u.getFriendLogins().contains(toUser))
                .orElse(false);
        return isFriend
                ? amount.multiply(BigDecimal.valueOf(0.03))
                : amount.multiply(BigDecimal.valueOf(0.10));
    }
}
