package org.gerasic.storage.repository;

import org.gerasic.storage.entity.AccountEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountEventRepository extends JpaRepository<AccountEventEntity, Long> {}
