package org.gerasic.users;

import java.util.Objects;

/**
 * Builder for creating {@link User} objects. This class provides a fluent API for setting
 * the properties of the user and ensures that required fields are set before building the user.
 * It allows for the creation of a User object step by step.
 */
public class UserBuilder {
    private final String login;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;

    /**
     * Constructs a new builder for creating a {@link User}.
     * The login is a required field and must be provided at the time of creating the builder.
     *
     * @param login the login of the user, must not be null
     * @throws NullPointerException if the provided login is null
     */
    public UserBuilder(String login) {
        this.login = Objects.requireNonNull(login, "Login cannot be null");
    }

    /**
     * Sets the name of the user.
     *
     * @param name the name of the user
     * @return the current instance of the builder
     * @throws NullPointerException if the provided name is null
     */
    public UserBuilder name(String name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        return this;
    }

    /**
     * Sets the age of the user.
     *
     * @param age the age of the user
     * @return the current instance of the builder
     */
    public UserBuilder age(int age) {
        this.age = age;
        return this;
    }

    /**
     * Sets the gender of the user.
     *
     * @param gender the gender of the user
     * @return the current instance of the builder
     * @throws NullPointerException if the provided gender is null
     */
    public UserBuilder gender(Gender gender) {
        this.gender = Objects.requireNonNull(gender, "Gender cannot be null");
        return this;
    }

    /**
     * Sets the hair color of the user.
     *
     * @param hairColor the hair color of the user
     * @return the current instance of the builder
     * @throws NullPointerException if the provided hair color is null
     */
    public UserBuilder hairColor(HairColor hairColor) {
        this.hairColor = Objects.requireNonNull(hairColor, "HairColor cannot be null");
        return this;
    }

    /**
     * Builds the {@link User} object with the parameters provided so far.
     * The method ensures that required fields (name, gender, hair color) are set before building.
     *
     * @return the constructed {@link User} object
     * @throws NullPointerException if any of the required fields (name, gender, hairColor) are missing
     */
    public User build() {
        Objects.requireNonNull(name, "Name must be set before building");
        Objects.requireNonNull(gender, "Gender must be set before building");
        Objects.requireNonNull(hairColor, "HairColor must be set before building");

        return new User(login, name, age, gender, hairColor);
    }
}
