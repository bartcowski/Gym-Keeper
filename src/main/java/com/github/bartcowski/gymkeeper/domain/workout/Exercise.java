package com.github.bartcowski.gymkeeper.domain.workout;

import java.util.List;
import java.util.Objects;

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

    public ExerciseId id() {
        return id;
    }

    public int index() {
        return index;
    }

    public ExerciseType exerciseType() {
        return exerciseType;
    }

    public List<ExerciseSet> sets() {
        return sets;
    }

    public String comment() {
        return comment;
    }

    public void update(Exercise updatedExercise) {
        this.index = updatedExercise.index();
        this.exerciseType = updatedExercise.exerciseType();
        this.comment = updatedExercise.comment();
        this.sets = updatedExercise.sets();
    }

    public void decrementIndex() {
        this.index--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(id, exercise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
