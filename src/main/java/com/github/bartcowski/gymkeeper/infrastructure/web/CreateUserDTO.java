package com.github.bartcowski.gymkeeper.infrastructure.web;

import com.github.bartcowski.gymkeeper.domain.user.*;

class CreateUserDTO {

    String username;

    String gender;

    int age;

    double weight;

    int height;

    CreateUserCommand toDomain() {
        return new CreateUserCommand(
                new Username(this.username),
                gender.equalsIgnoreCase("male") ? UserGender.MALE : UserGender.FEMALE,
                new UserAge(this.age),
                new UserWeight(this.weight),
                new UserHeight(this.height)
        );
    }
}
