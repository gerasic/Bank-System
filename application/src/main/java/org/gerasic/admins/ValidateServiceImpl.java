package org.gerasic.admins;

import org.gerasic.contracts.admins.ValidateService;
import org.gerasic.exceptions.*;
import org.gerasic.persistance.entities.AccountEntity;
import org.gerasic.persistance.entities.UserEntity;
import org.gerasic.persistance.repositories.AccountRepository;
import org.gerasic.persistance.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidateServiceImpl implements ValidateService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public ValidateServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }
    @Override
    public void validateUserNotExists(String login) {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new UserAlreadyExistsException("User " + login + " already exists");
        }
    }

    @Override
    public UserEntity validateUserExists(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User " + login + " not found"));
    }

    @Override
    public void validateNotSameLogin(String login, String friendLogin) {
        if (login.equals(friendLogin)) {
            throw new SameLoginException("User cannot perform this action on themselves: " + login);
        }
    }

    @Override
    public AccountEntity validateAccountExists(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
    }

    @Override
    public void validateAccountNotExists(Long accountId) {
        if (accountRepository.findById(accountId).isPresent()) {
            throw new AccountAlreadyExistsException("Account " + accountId + " already exists");
        }
    }

    @Override
    public void validateSufficientFunds(Long accountId, BigDecimal amount) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
    }
}
