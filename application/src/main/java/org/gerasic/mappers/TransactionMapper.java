package org.gerasic.mappers;

import org.gerasic.persistance.entities.AccountEntity;
import org.gerasic.persistance.entities.TransactionEntity;
import org.gerasic.transactions.Transaction;

/**
 * Utility class for mapping between the Transaction and TransactionEntity objects.
 * Provides methods to convert a TransactionEntity object to a Transaction model object and vice versa.
 */
public class TransactionMapper {

    /**
     * Converts a TransactionEntity object to a Transaction model object.
     *
     * @param transactionEntity the TransactionEntity object to convert
     * @return the corresponding Transaction model object, or null if the input entity is null
     */
    public static Transaction toModel(TransactionEntity transactionEntity) {
        if (transactionEntity == null) {
            return null;
        }

        return new Transaction(
                transactionEntity.getId().toString(),
                transactionEntity.getAmount(),
                transactionEntity.getType());
    }

    /**
     * Converts a Transaction model object to a TransactionEntity object.
     *
     * @param transaction the Transaction model object to convert
     * @param accountEntity the AccountEntity object associated with the transaction
     * @return the corresponding TransactionEntity object, or null if either the transaction or accountEntity is null
     */
    public static TransactionEntity toEntity(Transaction transaction, AccountEntity accountEntity) {
        if (transaction == null || accountEntity == null) {
            return null;
        }

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAccount(accountEntity);
        transactionEntity.setAmount(transaction.getAmount());
        transactionEntity.setType(transaction.getType());

        return transactionEntity;
    }
}
