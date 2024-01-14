package com.github.bartcowski.gymkeeper.domain.user;

public record UserAge(int value) {

    public UserAge {
        if (value <= 0 || value > 120) {
            throw new IllegalArgumentException("User must be between 1 and 120 years old");
        }
    }

}
