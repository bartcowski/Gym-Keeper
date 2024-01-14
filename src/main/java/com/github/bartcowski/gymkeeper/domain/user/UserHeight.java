package com.github.bartcowski.gymkeeper.domain.user;

public record UserHeight(int value) {

    public UserHeight {
        if (value <= 0 || value > 300) {
            throw new IllegalArgumentException("User height must be between 1-300 cm");
        }
    }

    public double inMetres() {
        return value() / 100.0;
    }

}
