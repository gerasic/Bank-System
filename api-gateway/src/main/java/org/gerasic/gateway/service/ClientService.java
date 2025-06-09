package org.gerasic.gateway.service;

import org.gerasic.gateway.dao.clients.AccountClient;
import org.gerasic.gateway.dao.clients.FriendClient;
import org.gerasic.gateway.dao.clients.TransactionClient;
import org.gerasic.gateway.dao.clients.UserClient;
import org.gerasic.gateway.dto.AccountResponse;
import org.gerasic.gateway.dto.TransactionResponse;
import org.gerasic.gateway.dto.UserResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final UserClient userClient;
    private final AccountClient accountClient;
    private final FriendClient friendClient;
    private final TransactionClient transactionClient;

    public ClientService(UserClient userClient,
                         AccountClient accountClient,
                         FriendClient friendClient,
                         TransactionClient transactionClient) {
        this.userClient = userClient;
        this.accountClient = accountClient;
        this.friendClient = friendClient;
        this.transactionClient = transactionClient;
    }

    private String currentLogin() {
        var auth = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return auth.getUsername();
    }

    public Optional<UserResponse> getMe() {
        return userClient.findByLogin(currentLogin());
    }

    public List<AccountResponse> getMyAccounts() {
        return accountClient.findByUserLogin(currentLogin());
    }

    public void addFriend(String friendLogin) {
        friendClient.addFriend(currentLogin(), friendLogin);
    }

    public void removeFriend(String friendLogin) {
        friendClient.removeFriend(currentLogin(), friendLogin);
    }

    public TransactionResponse deposit(Long accountId, BigDecimal amount) {
        validateAccount(accountId);

        return transactionClient.deposit(accountId, amount);
    }

    public TransactionResponse withdraw(Long accountId, BigDecimal amount) {
        validateAccount(accountId);

        return transactionClient.withdraw(accountId, amount);
    }

    public TransactionResponse transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount) {
        validateAccount(sourceAccountId);

        return transactionClient.transfer(sourceAccountId, targetAccountId, amount);
    }

    public AccountResponse getAccountById(Long accountId) {
        validateAccount(accountId);

        return accountClient.findAccountById(accountId);
    }

    private boolean validateAccount(Long accountId) {
        AccountResponse account = accountClient.findAccountById(accountId);
        if (account == null) {
            throw new AccessDeniedException("Access denied for account with id" + accountId);
        } else if (!account.ownerLogin().equals(currentLogin())) {
            throw new AccessDeniedException("Access denied for account with id" + accountId);
        }

        return account.ownerLogin().equals(currentLogin());
    }
}
