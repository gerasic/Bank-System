package org.gerasic.accounts;

import org.gerasic.transactions.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a bank account associated with a user, including balance, transaction history, and account details.
 * Provides methods to manage the account balance and view the transaction history.
 */
public class Account {
    private final Long id;
    private final String ownerLogin;
    private final List<Transaction> transactionHistory;
    private BigDecimal balance;

    /**
     * Creates a new account with the specified owner login and account ID.
     * Initializes the balance to zero and sets up an empty transaction history.
     * @param ownerLogin the login of the account owner
     * @param id the account ID
     * @throws NullPointerException if the ownerLogin is null
     */
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

    /**
     * Gets the account ID.
     * @return the account ID
     */
    public Long getId() {
        return id;
    }

    public String getIdString() {
        return id.toString();
    }

    /**
     * Gets the login of the account owner.
     * @return the owner's login
     */
    public String getOwnerLogin() {
        return ownerLogin;
    }

    /**
     * Gets the current balance of the account.
     * @return the current account balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the current balance of the account.
     * @param balance the new balance to set
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Gets the transaction history of the account.
     * @return an unmodifiable list of transactions associated with the account
     */
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    /**
     * Provides a string representation of the account, including its ID, owner login, balance, and the number of transactions.
     * @return a string representation of the account
     */
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
