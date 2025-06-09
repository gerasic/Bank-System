package org.gerasic.contracts.admins;

import org.gerasic.persistance.entities.AccountEntity;
import org.gerasic.persistance.entities.UserEntity;

import java.math.BigDecimal;

public interface ValidateService {
    void validateUserNotExists(String login);
    UserEntity validateUserExists(String login);
    void validateNotSameLogin(String login, String friendLogin);
    void validateAccountNotExists(Long accountId);
    AccountEntity validateAccountExists(Long accountId);
    void validateSufficientFunds(Long accountId, BigDecimal amount);
//    void validateTransferAmount(String fromAccountId, String toAccountId, BigDecimal amount);
}
