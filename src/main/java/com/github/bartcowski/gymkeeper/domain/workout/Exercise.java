package com.github.bartcowski.gymkeeper.domain.workout;

import java.util.List;

public record Exercise(ExerciseId id, ExerciseType exerciseType, List<ExerciseSet> sets, String comment) {

    public Exercise(ExerciseId id, ExerciseType exerciseType, List<ExerciseSet> sets) {
        this(id, exerciseType, sets, "");
    }

}
