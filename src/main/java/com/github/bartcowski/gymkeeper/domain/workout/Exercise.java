package com.github.bartcowski.gymkeeper.domain.workout;

import java.util.List;

public record Exercise(ExerciseType exerciseType, List<ExerciseSet> sets, String comment) {

    public Exercise(ExerciseType exerciseType, List<ExerciseSet> sets) {
        this(exerciseType, sets, "");
    }

}
