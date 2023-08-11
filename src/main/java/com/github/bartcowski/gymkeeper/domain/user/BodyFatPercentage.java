package com.github.bartcowski.gymkeeper.domain.user;

public record BodyFatPercentage(double value) {

    public BodyFatPercentage {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Body Fat percentage must be between 0-100%");
        }
    }
}
