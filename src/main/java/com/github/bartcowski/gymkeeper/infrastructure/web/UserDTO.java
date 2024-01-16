package com.github.bartcowski.gymkeeper.infrastructure.web;

import com.github.bartcowski.gymkeeper.domain.user.User;

class UserDTO {

    long id;

    String username;

    String gender;

    int age;

    double weight;

    int height;

    UserDTO(long id, String username, String gender, int age, double weight, int height) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    static UserDTO fromDomain(User user) {
        return new UserDTO(
                user.getId().id(),
                user.getUsername().username(),
                user.getGender().toString(),
                user.getAge().value(),
                user.getWeight().value(),
                user.getHeight().value()
        );
    }
}
