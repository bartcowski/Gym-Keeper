package com.github.bartcowski.gymkeeper.domain.user;

public record CreateUserCommand(
        Username username,
        UserGender gender,
        UserAge age,
        UserWeight weight,
        UserHeight height
) {
}
