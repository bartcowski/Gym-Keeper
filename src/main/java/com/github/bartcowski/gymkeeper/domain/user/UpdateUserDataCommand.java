package com.github.bartcowski.gymkeeper.domain.user;

public record UpdateUserDataCommand(
        UserAge age,
        UserWeight weight,
        UserHeight height
) {
}
