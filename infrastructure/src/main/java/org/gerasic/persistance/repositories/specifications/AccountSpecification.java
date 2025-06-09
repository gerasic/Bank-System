package org.gerasic.persistance.repositories.specifications;

import org.gerasic.persistance.entities.AccountEntity;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {
    public static Specification<AccountEntity> hasOwnerLogin(String ownerLogin) {
        return (root, query, cb) -> ownerLogin == null ? null :
                cb.equal(root.get("ownerLogin"), ownerLogin);
    }
}