package org.gerasic.persistance.repositories.specifications;

import org.gerasic.persistance.entities.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<UserEntity> hasName(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.equal(root.get("name"), name);
    }

    public static Specification<UserEntity> hasGender(String gender) {
        return (root, query, cb) -> gender == null ? null :
                cb.equal(root.get("gender"), gender);
    }

    public static Specification<UserEntity> hasHairColor(String hairColor) {
        return (root, query, cb) -> hairColor == null ? null :
                cb.equal(root.get("hairColor"), hairColor);
    }

    public static Specification<UserEntity> hasAge(Integer age) {
        return (root, query, cb) -> age == null ? null :
                cb.equal(root.get("age"), age);
    }
}
