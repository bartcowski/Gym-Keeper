package com.github.bartcowski.gymkeeper.domain.user;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserAge(int age) {

    public UserAge {
        if (age <= 0 || age > 120) {
            throw new IllegalArgumentException("User must be between 1 and 120 years old");
        }
    }
}
