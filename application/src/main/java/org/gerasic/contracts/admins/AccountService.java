package org.gerasic.contracts.admins;

import org.gerasic.accounts.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    void createAccount(String login);

    Account getAccountById(Long id);

    List<Account> getUserAccounts(String login);

    List<Account> getAllAccounts(String ownerLogin);

    BigDecimal getBalance(Long accountId);
}
