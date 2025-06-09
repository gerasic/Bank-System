package org.gerasic.persistance.repositories;

import org.gerasic.persistance.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<UserEntity, String>,
        JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByLogin(String login);
}
