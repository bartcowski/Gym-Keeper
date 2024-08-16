package com.github.bartcowski.gymkeeper.domain.workout;

import lombok.Value;

@Value
public class ExerciseSet {

    int index;

    int reps;

    double weight;

    public ExerciseSet(int index, int reps, double weight) {
        if (index <= 0 || index > 1000) {
            throw new IllegalArgumentException("Index number must be between 1 and 1000");
        }
        if (reps <= 0 || reps > 1000) {
            throw new IllegalArgumentException("Reps number must be between 1 and 1000");
        }
        if (weight < 0.0) {
            throw new IllegalArgumentException("Lifted weight must not be negative");
        }
        this.index = index;
        this.reps = reps;
        this.weight = weight;
    }
}
