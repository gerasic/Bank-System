package org.gerasic.persistance.repositories;

import org.gerasic.persistance.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends
        JpaRepository<AccountEntity, Long>,
        JpaSpecificationExecutor<AccountEntity> {

    List<AccountEntity> findAllByOwnerLogin(String ownerLogin);
}
