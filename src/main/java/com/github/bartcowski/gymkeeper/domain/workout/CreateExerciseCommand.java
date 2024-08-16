package com.github.bartcowski.gymkeeper.domain.workout;

import java.util.List;

public record CreateExerciseCommand(
        List<ExerciseSet> sets,
        int index,
        ExerciseType exerciseType,
        String comment) {
}
