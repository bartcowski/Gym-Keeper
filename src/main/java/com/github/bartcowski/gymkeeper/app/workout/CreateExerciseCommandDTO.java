package com.github.bartcowski.gymkeeper.app.workout;

import com.github.bartcowski.gymkeeper.domain.workout.CreateExerciseCommand;
import com.github.bartcowski.gymkeeper.domain.workout.ExerciseType;

import java.util.List;

public class CreateExerciseCommandDTO {

    public List<ExerciseSetDTO> sets;

    public int index;

    public String exerciseType;

    public String comment;

    public CreateExerciseCommandDTO(
            List<ExerciseSetDTO> sets,
            int index,
            String exerciseType,
            String comment) {
        this.sets = sets;
        this.index = index;
        this.exerciseType = exerciseType;
        this.comment = comment;
    }

    public CreateExerciseCommand toDomain() {
        return new CreateExerciseCommand(
                sets.stream().map(ExerciseSetDTO::toDomain).toList(),
                index,
                ExerciseType.valueOf(exerciseType),
                comment
        );
    }
}
