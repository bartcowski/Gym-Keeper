package com.github.bartcowski.gymkeeper.domain.workout;

public record ExerciseSet(int reps, double weight) {

    public ExerciseSet {
        if (reps <= 0 || reps > 1000) {
            throw new IllegalArgumentException("Reps number must be between 1 and 1000");
        }
        if (weight < 0.0) {
            throw new IllegalArgumentException("Lifted weight must not be negative");
        }
    }
}
