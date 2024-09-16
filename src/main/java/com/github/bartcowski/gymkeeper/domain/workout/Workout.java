package com.github.bartcowski.gymkeeper.domain.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public class Workout {

    private final WorkoutId id;

    private final UserId userId;

    private final List<Exercise> exercises;

    private LocalDate date;

    private boolean isDeload;

    private String comment;

    public Workout(WorkoutId id, UserId userId, List<Exercise> exercises, LocalDate date, boolean isDeload, String comment) {
        this.id = id;
        this.userId = userId;
        this.exercises = exercises; //TODO: some validation?
        this.date = date;
        this.isDeload = isDeload;
        this.comment = comment;
    }

    public Workout(WorkoutId id, UserId userId, LocalDate date, boolean isDeload, String comment) {
        this.id = id;
        this.userId = userId;
        this.exercises = new ArrayList<>();
        this.date = date;
        this.isDeload = isDeload;
        this.comment = comment;
    }

    public WorkoutId id() {
        return id;
    }

    public UserId userId() {
        return userId;
    }

    public List<Exercise> exercises() {
        return exercises;
    }

    public LocalDate date() {
        return date;
    }

    public boolean deload() {
        return isDeload;
    }

    public String comment() {
        return comment;
    }

    public void updateWorkout(UpdateWorkoutCommand command) {
        this.date = command.date();
        this.isDeload = command.isDeload();
        this.comment = command.comment();
    }

    public void addExercise(Exercise newExercise) {
        validateExerciseToAddOrUpdate(newExercise);
        exercises.add(newExercise);
    }

    public void updateExercise(Exercise updatedExercise) {
        validateExerciseToAddOrUpdate(updatedExercise);
        findExerciseById(updatedExercise.id())
                .orElseThrow(() -> new IllegalStateException(
                        "Exercise " + updatedExercise.id().id() + " cannot be found in workout " + this.id))
                .update(updatedExercise);
    }

    public void deleteExercise(ExerciseId exerciseId) {
        Exercise exerciseToRemove = null;
        for (Exercise exercise : exercises) {
            if (exerciseToRemove != null) {
                exercise.decrementIndex();
            }
            if (exercise.id().equals(exerciseId)) {
                exerciseToRemove = exercise;
            }
        }
        exercises.remove(exerciseToRemove);
    }

    private void validateExerciseToAddOrUpdate(Exercise exerciseToValidate) {
        validateIfContainsDuplicates(exerciseToValidate.sets(), ExerciseSet::index);
        for (Exercise e : exercises) {
            if (e.id().equals(exerciseToValidate.id())) {
                continue;
            }
            if (e.exerciseType() == exerciseToValidate.exerciseType()) {
                throw new IllegalStateException("Exercises within workout can't have duplicate types, failed update of workout " + this.id);
            }
            if (e.index() == exerciseToValidate.index()) {
                throw new IllegalStateException("Exercises within workout can't have duplicate indexes, failed update of workout " + this.id);
            }
        }
    }

    private Optional<Exercise> findExerciseById(ExerciseId exerciseId) {
        return exercises.stream()
                .filter(e -> e.id().equals(exerciseId))
                .findFirst();
    }

    private <T, U> void validateIfContainsDuplicates(List<T> elements, Function<T, U> mapper) {
        List<U> mappedElements = elements.stream().map(mapper).toList();
        HashSet<U> mappedDistinctElements = new HashSet<>(mappedElements);
        if (mappedDistinctElements.size() < mappedElements.size()) {
            throw new IllegalStateException("Duplicate index found while trying to update exercises: " + elements);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(id, workout.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
