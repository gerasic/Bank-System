package org.gerasic.gateway.model.accounts;

import org.gerasic.gateway.model.transactions.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Account {
    private final Long id;
    private final String ownerLogin;
    private final List<Transaction> transactionHistory;
    private BigDecimal balance;

    public Account(String ownerLogin, Long id) {
        this.id = id;
        this.ownerLogin = Objects.requireNonNull(ownerLogin, "Owner login cannot be null");
        this.balance = BigDecimal.ZERO;
        this.transactionHistory = new ArrayList<>();
    }

    public Account(String ownerLogin, Long id, List<Transaction> transactionHistory) {
        this.id = id;
        this.ownerLogin = Objects.requireNonNull(ownerLogin, "Owner login cannot be null");
        this.balance = BigDecimal.ZERO;
        this.transactionHistory = new ArrayList<>(transactionHistory);
    }

    public Long getId() {
        return id;
    }

    public String getIdString() {
        return id.toString();
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", ownerLogin='" + ownerLogin + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactionHistory.size() +
                '}';
    }
}
