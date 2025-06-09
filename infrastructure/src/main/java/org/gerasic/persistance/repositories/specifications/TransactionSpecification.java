package org.gerasic.persistance.repositories.specifications;

import org.gerasic.persistance.entities.TransactionEntity;
import org.gerasic.persistance.entities.UserEntity;
import org.gerasic.transactions.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class TransactionSpecification {
    public static Specification<TransactionEntity> hasAmount(BigDecimal amount) {
        return (root, query, cb) -> amount == null ? null :
                cb.equal(root.get("amount"), amount);
    }

    public static Specification<TransactionEntity> hasAccountId(Long accountId) {
        return (root, query, cb) -> accountId == null ? null :
                cb.equal(root.get("account").get("id"), accountId);
    }

    public static Specification<TransactionEntity> hasTransactionType(TransactionType type) {
        return (root, query, cb) -> type == null ? null :
                cb.equal(root.get("type"), type);
    }
}
