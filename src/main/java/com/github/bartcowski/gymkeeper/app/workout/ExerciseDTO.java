package com.github.bartcowski.gymkeeper.app.workout;

import com.github.bartcowski.gymkeeper.domain.workout.Exercise;
import com.github.bartcowski.gymkeeper.domain.workout.ExerciseId;
import com.github.bartcowski.gymkeeper.domain.workout.ExerciseType;

import java.util.List;

public class ExerciseDTO {

    public long id;

    public int index;

    public String exerciseType;

    public List<ExerciseSetDTO> sets;

    public String comment;

    public ExerciseDTO(long id, int index, String exerciseType, List<ExerciseSetDTO> sets, String comment) {
        this.id = id;
        this.index = index;
        this.exerciseType = exerciseType;
        this.sets = sets;
        this.comment = comment;
    }

    public static ExerciseDTO fromDomain(Exercise exercise) {
        return new ExerciseDTO(
                exercise.getId().id(),
                exercise.getIndex(),
                exercise.getExerciseType().name(),
                exercise.getSets().stream().map(ExerciseSetDTO::fromDomain).toList(),
                exercise.getComment()
        );
    }

    public Exercise toDomain() {
        return new Exercise(
                new ExerciseId(id),
                index,
                ExerciseType.valueOf(exerciseType),
                sets.stream().map(ExerciseSetDTO::toDomain).toList(),
                comment
        );
    }
}
