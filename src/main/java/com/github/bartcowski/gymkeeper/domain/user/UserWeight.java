package com.github.bartcowski.gymkeeper.domain.user;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserWeight(double weight) {

    public UserWeight {
        if (weight <= 0 || weight > 500) {
            throw new IllegalArgumentException("User weight must be between 1-500 kg");
        }
    }
}
