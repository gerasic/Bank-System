package org.gerasic.storage.repository;

import org.gerasic.storage.entity.ClientEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientEventRepository extends JpaRepository<ClientEventEntity, Long> {}
