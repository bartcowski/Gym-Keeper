package com.github.bartcowski.gymkeeper.domain.weightlog;

public record WeightLogName(String name) {

    public WeightLogName {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("WeightLog name must not be empty");
        }
    }
}
