package com.github.bartcowski.gymkeeper.domain.user;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserHeight(int height) {

    public UserHeight {
        if (height <= 0 || height > 300) {
            throw new IllegalArgumentException("User height must be between 1-300 cm");
        }
    }

    public double inMetres() {
        return height() / 100.0;
    }

}
