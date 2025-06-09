package org.gerasic.persistance.entities;

import jakarta.persistence.*;
import org.gerasic.users.Gender;
import org.gerasic.users.HairColor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a user in the system.
 * This class is mapped to the "users" table in the database.
 */
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private String login;

    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "hair_color")
    private HairColor hairColor;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_login"))
    @Column(name = "friend_login")
    private final Set<String> friendLogins = new HashSet<>();

    /**
     * Constructor to create a user entity with specified values.
     *
     * @param login the login of the user
     * @param name the name of the user
     * @param age the age of the user
     * @param gender the gender of the user
     * @param hairColor the hair color of the user
     */
    public UserEntity(String login, String name, int age, Gender gender, HairColor hairColor) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
    }

    /**
     * Constructor to create a user entity with specified values and a set of friend logins.
     *
     * @param login the login of the user
     * @param name the name of the user
     * @param age the age of the user
     * @param gender the gender of the user
     * @param hairColor the hair color of the user
     * @param friendLogins the set of friend logins associated with the user
     */
    public UserEntity(String login, String name, int age, Gender gender, HairColor hairColor, Set<String> friendLogins) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
        this.friendLogins.addAll(friendLogins);
    }

    /**
     * Default constructor for JPA.
     */
    public UserEntity() {
    }

    /**
     * Gets the login of the user.
     *
     * @return the login of the user
     */
    public String getLogin() {
        return login;
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the user.
     *
     * @return the age of the user
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
     * @return the gender of the user
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender of the user.
     *
     * @param gender the new gender of the user
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the hair color of the user.
     *
     * @return the hair color of the user
     */
    public HairColor getHairColor() {
        return hairColor;
    }

    /**
     * Sets the hair color of the user.
     *
     * @param hairColor the new hair color of the user
     */
    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }

    /**
     * Gets the set of friend logins associated with the user.
     *
     * @return the set of friend logins
     */
    public Set<String> getFriendLogins() {
        return friendLogins;
    }

    /**
     * Adds a friend login to the user's friend list.
     *
     * @param friendLogin the login of the friend to be added
     */
    public void addFriend(String friendLogin) {
        friendLogins.add(friendLogin);
    }

    /**
     * Removes a friend login from the user's friend list.
     *
     * @param friendLogin the login of the friend to be removed
     */
    public void removeFriend(String friendLogin) {
        friendLogins.remove(friendLogin);
    }
}
