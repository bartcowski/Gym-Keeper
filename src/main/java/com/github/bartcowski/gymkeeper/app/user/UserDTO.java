package com.github.bartcowski.gymkeeper.app.user;

import com.github.bartcowski.gymkeeper.domain.user.User;

public class UserDTO {

    public long id;

    public String username;

    public String gender;

    public int age;

    public double weight;

    public int height;

    public UserDTO(long id, String username, String gender, int age, double weight, int height) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public static UserDTO fromDomain(User user) {
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
