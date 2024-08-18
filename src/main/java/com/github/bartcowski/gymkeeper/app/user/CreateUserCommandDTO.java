package com.github.bartcowski.gymkeeper.app.user;

import com.github.bartcowski.gymkeeper.domain.user.*;

public class CreateUserCommandDTO {

    public String username;

    public String gender;

    public int age;

    public double weight;

    public int height;

    public CreateUserCommand toDomain() {
        return new CreateUserCommand(
                new Username(this.username),
                gender.equalsIgnoreCase("male") ? UserGender.MALE : UserGender.FEMALE,
                new UserAge(this.age),
                new UserWeight(this.weight),
                new UserHeight(this.height)
        );
    }
}
