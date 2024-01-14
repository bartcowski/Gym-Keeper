package com.github.bartcowski.gymkeeper.domain.user;

public record UserWeight(double value) {

    public UserWeight {
        if (value <= 0 || value > 500) {
            throw new IllegalArgumentException("User weight must be between 1-500 kg");
        }
    }
}
