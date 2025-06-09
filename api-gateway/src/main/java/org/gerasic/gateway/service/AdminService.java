package org.gerasic.gateway.service;

import org.gerasic.gateway.dao.clients.UserClient;
import org.gerasic.gateway.dao.clients.AccountClient;
import org.gerasic.gateway.dao.persistance.entity.UserEntity;
import org.gerasic.gateway.dao.persistance.repository.UserRepository;
import org.gerasic.gateway.dto.AccountResponse;
import org.gerasic.gateway.dto.CreateAdminRequest;
import org.gerasic.gateway.dto.CreateUserRequest;
import org.gerasic.gateway.dto.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final UserClient userClient;
    private final AccountClient accountClient;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserClient userClient,
                        AccountClient accountClient,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
        this.userClient = userClient;
        this.accountClient = accountClient;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(CreateUserRequest user) {
        UserEntity userEntity = new UserEntity(user.login(), passwordEncoder.encode(user.password()), "CLIENT", user.name());

        userRepository.save(userEntity);
        userClient.createUser(user);
    }

    public void createAdmin(CreateAdminRequest admin) {
        UserEntity userEntity = new UserEntity(admin.login(), passwordEncoder.encode(admin.password()), "ADMIN", admin.name());
        userRepository.save(userEntity);
    }

    public List<UserResponse> getAllUsers(String gender, String hairColor) {
        return userClient.findAll(gender, hairColor);
    }

    public Optional<UserResponse> getUserByLogin(String login) {
        return userClient.findByLogin(login);
    }

    public List<AccountResponse> getAllAccounts() {
        return accountClient.findAllAccounts();
    }

    public List<AccountResponse> getAccountsByUserLogin(String login) {
        return accountClient.findByUserLogin(login);
    }
}
