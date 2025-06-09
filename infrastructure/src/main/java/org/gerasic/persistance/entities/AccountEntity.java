package org.gerasic.persistance.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity representing a bank account.
 * This class is mapped to the "accounts" table in the database.
 */
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_login", nullable = false)
    private String ownerLogin;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactionHistory;

    /**
     * Constructor to create an AccountEntity with the specified owner login.
     * The balance is initialized to zero, and the transaction history is initialized as an empty list.
     *
     * @param ownerLogin the login of the account owner
     * @throws NullPointerException if the ownerLogin is null
     */
    public AccountEntity(String ownerLogin) {
        this.ownerLogin = Objects.requireNonNull(ownerLogin, "Owner login cannot be null");
        this.balance = BigDecimal.ZERO;
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Default constructor for JPA.
     */
    public AccountEntity() {
    }

    /**
     * Gets the ID of the account.
     *
     * @return the ID of the account
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the login of the account owner.
     *
     * @return the login of the account owner
     */
    public String getOwnerLogin() {
        return ownerLogin;
    }

    /**
     * Gets the balance of the account.
     *
     * @return the balance of the account
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the account.
     *
     * @param balance the new balance of the account
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Gets the transaction history of the account.
     *
     * @return the list of transactions related to this account
     */
    public List<TransactionEntity> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * Sets the transaction history for the account.
     *
     * @param transactionHistory the list of transactions to set
     */
    public void setTransactionHistory(List<TransactionEntity> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    /**
     * Adds a transaction to the account's transaction history.
     *
     * @param transaction the transaction to add
     */
    public void addTransaction(TransactionEntity transaction) {
        transactionHistory.add(transaction);
    }
}
