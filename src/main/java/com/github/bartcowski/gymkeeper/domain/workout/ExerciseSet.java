package com.github.bartcowski.gymkeeper.domain.workout;

public record ExerciseSet(int index, int reps, double weight) {

    public ExerciseSet {
        if (index <= 0 || index > 1000) {
            throw new IllegalArgumentException("Index number must be between 1 and 1000");
        }
        if (reps <= 0 || reps > 1000) {
            throw new IllegalArgumentException("Reps number must be between 1 and 1000");
        }
        if (weight < 0.0) {
            throw new IllegalArgumentException("Lifted weight must not be negative");
        }
    }
}
