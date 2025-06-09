package org.gerasic.gateway.config;

import org.gerasic.gateway.dao.persistance.entity.UserEntity;
import org.gerasic.gateway.dao.persistance.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner createAdmin(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByLogin("gerasic").isEmpty()) {
                var admin = new UserEntity(
                        "gerasic",
                        encoder.encode("Gerasic18.08"),
                        "ADMIN",
                        "Nikita"
                );
                repo.save(admin);
                System.out.println("Admin 'gerasic' created.");
            }
        };
    }
}
