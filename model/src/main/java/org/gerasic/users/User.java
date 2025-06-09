package org.gerasic.users;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a user with a login, personal information (name, age, gender, hair color),
 * and a set of friends. Provides methods for managing the user's friends and accessing their data.
 */
public class User {
    private final String login;
    private final Set<String> friendLogins;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;

    /**
     * Creates a new user with the specified login, name, age, gender, and hair color.
     * Initializes an empty set of friends.
     *
     * @param login the unique login of the user
     * @param name the name of the user
     * @param age the age of the user
     * @param gender the gender of the user
     * @param hairColor the hair color of the user
     * @throws NullPointerException if any of the parameters are null
     */
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

    /**
     * Gets the login of the user.
     *
     * @return the user's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Gets the name of the user.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the new name of the user
     * @throws NullPointerException if the provided name is null
     */
    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }

    /**
     * Gets the age of the user.
     *
     * @return the user's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the user.
     *
     * @param age the new age of the user
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the gender of the user.
     *
     * @return the user's gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender of the user.
     *
     * @param gender the new gender of the user
     * @throws NullPointerException if the provided gender is null
     */
    public void setGender(Gender gender) {
        this.gender = Objects.requireNonNull(gender, "Gender cannot be null");
    }

    /**
     * Gets the hair color of the user.
     *
     * @return the user's hair color
     */
    public HairColor getHairColor() {
        return hairColor;
    }

    /**
     * Sets the hair color of the user.
     *
     * @param hairColor the new hair color of the user
     * @throws NullPointerException if the provided hair color is null
     */
    public void setHairColor(HairColor hairColor) {
        this.hairColor = Objects.requireNonNull(hairColor, "HairColor cannot be null");
    }

    /**
     * Gets the set of friends associated with this user.
     *
     * @return an unmodifiable set of friends
     */
    public Set<String> getFriendLogins() {
        return friendLogins;
    }

    /**
     * Adds a friend to the user's friend list. The user cannot add themselves as a friend.
     *
     * @param friend the friend to add
     * @throws IllegalArgumentException if the friend is null or the same as this user
     */
    public void addFriend(User friend) {
        if (friend == null || friend.equals(this)) {
            throw new IllegalArgumentException("Invalid friend");
        }
        friendLogins.add(friend.getLogin());
    }

    /**
     * Removes a friend from the user's friend list.
     *
     * @param friend the friend to remove
     */
    public void removeFriend(User friend) {
        friendLogins.remove(friend);
    }

    /**
     * Checks if two users are equal based on their login.
     *
     * @param o the object to compare
     * @return true if the logins are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return login.equals(user.login);
    }

    /**
     * Generates a hash code for the user based on their login.
     *
     * @return the hash code of the user
     */
    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    /**
     * Provides a string representation of the user, including login, name, age, gender, hair color, and friends count.
     *
     * @return a string representation of the user
     */
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
