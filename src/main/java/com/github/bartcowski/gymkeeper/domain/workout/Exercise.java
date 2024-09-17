package com.github.bartcowski.gymkeeper.domain.workout;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "exercise")
public class Exercise {

    @EmbeddedId
    private ExerciseId id;

    private int index;

    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;

    @ElementCollection
    @CollectionTable(name = "exercise_set", joinColumns = @JoinColumn(name = "exercise_id"))
    private List<ExerciseSet> sets;

    private String comment;

    @Version
    private int version;

    public Exercise(ExerciseId id, int index, ExerciseType exerciseType, List<ExerciseSet> sets, String comment) {
        this.id = id;
        this.index = index;
        this.exerciseType = exerciseType;
        this.sets = sets;
        this.comment = comment;
        this.version = 1;
    }

    public Exercise(ExerciseId id, int index, ExerciseType exerciseType, List<ExerciseSet> sets) {
        this(id, index, exerciseType, sets, "");
    }

    protected Exercise() {
        //persistence
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
