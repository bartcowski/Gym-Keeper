package com.github.bartcowski.gymkeeper.domain.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Value
public class Workout {

    WorkoutId id;

    UserId userId; //workout creator, loose coupling between User and Workout

    List<Exercise> exercises;

    LocalDate date;

    boolean isDeload;

    String comment;

    public Workout(WorkoutId id, UserId userId, List<Exercise> exercises, LocalDate date, boolean isDeload, String comment) {
        this.id = id;
        this.userId = userId;
        this.exercises = new ArrayList<>();
        this.date = date;
        this.isDeload = isDeload;
        this.comment = comment;
        updateExercises(exercises);
    }

    public Workout(WorkoutId id, UserId userId, LocalDate date, boolean isDeload, String comment) {
        this.id = id;
        this.userId = userId;
        this.exercises = new ArrayList<>();
        this.date = date;
        this.isDeload = isDeload;
        this.comment = comment;
    }

    public void updateExercises(List<Exercise> updatedExercises) {
        validateUpdatedExercises(updatedExercises);
        for (Exercise e : updatedExercises) {
            addOrReplaceExercise(e);
        }
    }

    public void updateExercise(Exercise updatedExercise) {
        addOrReplaceExercise(updatedExercise);
    }

    private void validateUpdatedExercises(List<Exercise> exercises) {
        validateIfContainsDuplicates(exercises, Exercise::exerciseType);
        for (Exercise e : exercises) {
            validateIfContainsDuplicates(e.sets(), ExerciseSet::index);
        }
    }

    private <T, U> void validateIfContainsDuplicates(List<T> elements, Function<T, U> mapper) {
        List<U> mappedElements = elements.stream().map(mapper).toList();
        HashSet<U> mappedDistinctElements = new HashSet<>(mappedElements);
        if (mappedDistinctElements.size() < mappedElements.size()) {
            throw new IllegalStateException("Duplicate type or index found while trying to update exercises: " + elements);
        }
    }

    private void addOrReplaceExercise(Exercise e) {
        Optional<Exercise> exerciseOfTheSameType = findAlreadyExistingExerciseOfTheSameType(e);
        exerciseOfTheSameType.ifPresent(exercises::remove);
        exercises.add(e);
    }

    private Optional<Exercise> findAlreadyExistingExerciseOfTheSameType(Exercise exercise) {
        return exercises.stream()
                .filter(e -> e.exerciseType().equals(exercise.exerciseType()))
                .findFirst();
    }

}
