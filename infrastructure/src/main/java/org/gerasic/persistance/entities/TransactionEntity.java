package org.gerasic.persistance.entities;

import jakarta.persistence.*;
import org.gerasic.transactions.TransactionType;

import java.math.BigDecimal;

/**
 * Entity representing a transaction in the system.
 * This class is mapped to the "transactions" table in the database.
 */
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    /**
     * Default constructor for JPA.
     */
    public TransactionEntity() {
    }

    /**
     * Constructor to create a transaction entity with specified values.
     *
     * @param account the account associated with the transaction
     * @param amount the amount involved in the transaction
     * @param type the type of the transaction (deposit/withdrawal)
     */
    public TransactionEntity(AccountEntity account, BigDecimal amount, TransactionType type) {
        this.account = account;
        this.amount = amount;
        this.type = type;
    }

    /**
     * Gets the ID of the transaction.
     *
     * @return the ID of the transaction
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the account associated with the transaction.
     *
     * @return the account associated with the transaction
     */
    public AccountEntity getAccount() {
        return account;
    }

    /**
     * Sets the account associated with the transaction.
     *
     * @param account the account to be associated with the transaction
     */
    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    /**
     * Gets the amount of the transaction.
     *
     * @return the amount of the transaction
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the transaction.
     *
     * @param amount the amount of the transaction
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets the type of the transaction.
     *
     * @return the type of the transaction (e.g., deposit, withdrawal)
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Sets the type of the transaction.
     *
     * @param type the type of the transaction
     */
    public void setType(TransactionType type) {
        this.type = type;
    }
}
