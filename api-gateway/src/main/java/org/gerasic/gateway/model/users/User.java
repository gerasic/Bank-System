package org.gerasic.gateway.model.users;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private final String login;
    private final Set<String> friendLogins;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;

    public User(String login,
                String name,
                int age,
                Gender gender,
                HairColor hairColor) {
        this.login = Objects.requireNonNull(login, "Login cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.age = age;
        this.gender = Objects.requireNonNull(gender, "Gender cannot be null");
        this.hairColor = Objects.requireNonNull(hairColor, "HairColor cannot be null");
        this.friendLogins = new HashSet<>();
    }

    public User(String login,
                String name,
                int age,
                Gender gender,
                HairColor hairColor,
                Set<String> friendLogins) {
        this.login = Objects.requireNonNull(login, "Login cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.age = age;
        this.gender = Objects.requireNonNull(gender, "Gender cannot be null");
        this.hairColor = Objects.requireNonNull(hairColor, "HairColor cannot be null");
        this.friendLogins = friendLogins;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = Objects.requireNonNull(gender, "Gender cannot be null");
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    public void setHairColor(HairColor hairColor) {
        this.hairColor = Objects.requireNonNull(hairColor, "HairColor cannot be null");
    }

    public Set<String> getFriendLogins() {
        return friendLogins;
    }

    public void addFriend(User friend) {
        if (friend == null || friend.equals(this)) {
            throw new IllegalArgumentException("Invalid friend");
        }
        friendLogins.add(friend.getLogin());
    }

    public void removeFriend(User friend) {
        friendLogins.remove(friend);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return "User{" + "\n" +
                "login='" + login + '\'' + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", age=" + age + "\n" +
                ", gender=" + gender + "\n" +
                ", hairColor=" + hairColor + "\n" +
                ", friendsCount=" + friendLogins.size() + "\n" +
                '}';
    }
}
