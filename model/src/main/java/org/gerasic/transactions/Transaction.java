package org.gerasic.transactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a financial transaction involving an account, including the transaction amount, type, and timestamp.
 * Provides methods to retrieve transaction details.
 */
public class Transaction {
    private final String accountId;
    private final BigDecimal amount;
    private final TransactionType type;
    private final LocalDateTime timestamp;

    /**
     * Creates a new transaction with the specified account ID, amount, and type.
     * The timestamp is automatically set to the current time.
     *
     * @param accountId the ID of the account associated with the transaction
     * @param amount the transaction amount
     * @param type the type of transaction (e.g., deposit, withdrawal)
     * @throws NullPointerException if any of the parameters are null
     */
    public Transaction(String accountId, BigDecimal amount, TransactionType type) {
        this.accountId = Objects.requireNonNull(accountId, "Account ID cannot be null");
        this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
        this.type = Objects.requireNonNull(type, "Transaction type cannot be null");
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Gets the account ID associated with this transaction.
     *
     * @return the account ID
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Gets the amount of the transaction.
     *
     * @return the transaction amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Gets the type of the transaction (e.g., deposit, withdrawal).
     *
     * @return the transaction type
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Gets the timestamp when the transaction was created.
     *
     * @return the timestamp of the transaction
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Provides a string representation of the transaction, including account ID, amount, type, and timestamp.
     *
     * @return a string representation of the transaction
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", timestamp=" + timestamp +
                '}';
    }
}
