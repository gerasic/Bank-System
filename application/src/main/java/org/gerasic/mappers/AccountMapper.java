package org.gerasic.mappers;

import org.gerasic.accounts.Account;
import org.gerasic.persistance.entities.AccountEntity;

/**
 * Utility class for mapping between the Account and AccountEntity objects.
 * Provides methods to convert an AccountEntity object to an Account model object and vice versa.
 */
public class AccountMapper {

    /**
     * Converts an AccountEntity object to an Account model object.
     *
     * @param accountEntity the AccountEntity object to convert
     * @return the corresponding Account model object, or null if the input entity is null
     */
    public static Account toModel(AccountEntity accountEntity) {
        Account account = new Account(accountEntity.getOwnerLogin(), accountEntity.getId());

        account.setBalance(accountEntity.getBalance());
        accountEntity.getTransactionHistory().forEach(transactionEntity -> {
            account.addTransaction(TransactionMapper.toModel(transactionEntity));
        });

        return account;
    }

    /**
     * Converts an Account model object to an AccountEntity object.
     *
     * @param account the Account model object to convert
     * @return the corresponding AccountEntity object
     */
    public static AccountEntity toEntity(Account account) {
        AccountEntity accountEntity = new AccountEntity(account.getOwnerLogin());
        accountEntity.setBalance(account.getBalance());

        account.getTransactionHistory().forEach(transaction -> {
            accountEntity.addTransaction(TransactionMapper.toEntity(transaction, accountEntity));
        });

        return accountEntity;
    }
}
