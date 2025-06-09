package org.gerasic.contracts.admins;

import org.gerasic.users.Gender;
import org.gerasic.users.HairColor;
import org.gerasic.users.User;

import java.util.List;

public interface UserService {
    void createUser(String login, String name, int age, Gender gender, HairColor hairColor);

    User getUserInfo(String login);

    void deleteUser(String login);

    void addFriend(String login, String friendLogin);

    void removeFriend(String login, String friendLogin);

    List<String> getFriends(String login);

    List<User> getAllUsers(String name, String gender, String hairColor, Integer age);
}
