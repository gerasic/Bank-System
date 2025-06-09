package org.gerasic.admins;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gerasic.contracts.admins.UserService;
import org.gerasic.contracts.admins.ValidateService;
import org.gerasic.dto.ClientEventDto;
import org.gerasic.dto.ClientPayload;
import org.gerasic.mappers.UserMapper;
import org.gerasic.persistance.KafkaPublisher;
import org.gerasic.persistance.entities.UserEntity;
import org.gerasic.persistance.repositories.UserRepository;
import org.gerasic.persistance.repositories.specifications.UserSpecification;
import org.gerasic.users.Gender;
import org.gerasic.users.HairColor;
import org.gerasic.users.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidateService validator;
    private final KafkaPublisher kafkaPublisher;
    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository,
                           ValidateService validator, KafkaPublisher kafkaPublisher,
                           ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.kafkaPublisher = kafkaPublisher;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @Override
    public void createUser(String login, String name, int age, Gender gender, HairColor hairColor) {
        validator.validateUserNotExists(login);
        User user = new User(login, name, age, gender, hairColor);
        UserEntity saved = userRepository.save(UserMapper.toEntity(user));

        kafkaPublisher.publishClientEvent(saved.getLogin(), createEvent(saved, "CREATED"));
    }

    @Override
    public User getUserInfo(String login) {
        validator.validateUserExists(login);
        return userRepository.findByLogin(login)
                .map(UserMapper::toModel)
                .orElseThrow();
    }

    @Transactional
    @Override
    public void deleteUser(String login) {
        UserEntity user = validator.validateUserExists(login);
        userRepository.delete(user);

        kafkaPublisher.publishClientEvent(user.getLogin(), createEvent(user, "DELETED"));
    }

    @Transactional
    @Override
    public void addFriend(String login, String friendLogin) {
        validator.validateNotSameLogin(login, friendLogin);
        validator.validateUserExists(login);
        validator.validateUserExists(friendLogin);

        UserEntity user = userRepository.findByLogin(login).orElseThrow();
        UserEntity friend = userRepository.findByLogin(friendLogin).orElseThrow();

        user.addFriend(friendLogin);
        friend.addFriend(login);

        userRepository.save(user);
        userRepository.save(friend);

        kafkaPublisher.publishClientEvent(user.getLogin(), createEvent(user, "UPDATED"));
        kafkaPublisher.publishClientEvent(friend.getLogin(), createEvent(friend, "UPDATED"));
    }

    @Transactional
    @Override
    public void removeFriend(String login, String friendLogin) {
        validator.validateNotSameLogin(login, friendLogin);
        validator.validateUserExists(login);
        validator.validateUserExists(friendLogin);

        UserEntity user = userRepository.findByLogin(login).orElseThrow();
        UserEntity friend = userRepository.findByLogin(friendLogin).orElseThrow();

        user.removeFriend(friendLogin);
        friend.removeFriend(login);

        userRepository.save(user);
        userRepository.save(friend);

        kafkaPublisher.publishClientEvent(user.getLogin(), createEvent(user, "UPDATED"));
        kafkaPublisher.publishClientEvent(friend.getLogin(), createEvent(friend, "UPDATED"));
    }

    @Override
    public List<String> getFriends(String login) {
        UserEntity user = validator.validateUserExists(login);
        return user.getFriendLogins().stream().toList();
    }

    @Override
    public List<User> getAllUsers(String name, String gender, String hairColor, Integer age) {
        Specification<UserEntity> spec = Specification
                .where(UserSpecification.hasName(name))
                .and(UserSpecification.hasGender(gender))
                .and(UserSpecification.hasHairColor(hairColor))
                .and(UserSpecification.hasAge(age));

        return userRepository.findAll(spec).stream()
                .map(UserMapper::toModel)
                .toList();
    }

    private ClientEventDto createEvent(UserEntity user, String action) {
        ClientPayload payload = new ClientPayload(
                user.getLogin(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getHairColor()
        );
        return new ClientEventDto(
                user.getLogin(),
                action,
                Instant.now(),
                payload
        );
    }
}
