package com.github.bartcowski.gymkeeper.domain.workout;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public class Exercise {

    private final ExerciseId id;

    private int index;

    private ExerciseType exerciseType;

    private List<ExerciseSet> sets;

    private String comment;

    public Exercise(ExerciseId id, int index, ExerciseType exerciseType, List<ExerciseSet> sets, String comment) {
        this.id = id;
        this.index = index;
        this.exerciseType = exerciseType;
        this.sets = sets;
        this.comment = comment;
    }

    public Exercise(ExerciseId id, int index, ExerciseType exerciseType, List<ExerciseSet> sets) {
        this(id, index, exerciseType, sets, "");
    }

    public void update(Exercise updatedExercise) {
        this.index = updatedExercise.getIndex();
        this.exerciseType = updatedExercise.getExerciseType();
        this.comment = updatedExercise.getComment();
        this.sets = updatedExercise.getSets();
    }

    public void decrementIndex() {
        this.index--;
    }
}
