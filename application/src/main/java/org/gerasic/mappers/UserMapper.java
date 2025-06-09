package org.gerasic.mappers;

import org.gerasic.persistance.entities.UserEntity;
import org.gerasic.users.User;

import java.util.HashSet;

/**
 * Utility class for mapping between the User and UserEntity objects.
 * Provides methods to convert a User model object to a UserEntity object and vice versa.
 */
public class UserMapper {

    /**
     * Converts a User model object to a UserEntity object.
     *
     * @param user the User model object to convert
     * @return the corresponding UserEntity object, or null if the input user is null
     */
    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity(
                user.getLogin(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getHairColor(),
                new HashSet<String>(user.getFriendLogins())
        );

        entity.getFriendLogins().addAll(user.getFriendLogins());
        return entity;
    }

    /**
     * Converts a UserEntity object to a User model object.
     *
     * @param entity the UserEntity object to convert
     * @return the corresponding User model object, or null if the input entity is null
     */
    public static User toModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        User user = new User(
                entity.getLogin(),
                entity.getName(),
                entity.getAge(),
                entity.getGender(),
                entity.getHairColor(),
                new HashSet<String>(entity.getFriendLogins())
        );

        return user;
    }
}
