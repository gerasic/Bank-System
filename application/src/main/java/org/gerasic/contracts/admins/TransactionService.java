package org.gerasic.contracts.admins;

import org.gerasic.transactions.Transaction;
import org.gerasic.transactions.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void deposit(Long accountId, BigDecimal amount);

    void withdraw(Long accountId, BigDecimal amount);

    void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount);

    List<Transaction> getAllTransactions(Long accountId, BigDecimal amount, TransactionType type);
}
