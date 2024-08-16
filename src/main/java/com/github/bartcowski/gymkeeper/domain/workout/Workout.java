package com.github.bartcowski.gymkeeper.domain.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Getter
@EqualsAndHashCode
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
        findExerciseById(updatedExercise.getId())
                .orElseThrow(() -> new IllegalStateException(
                        "Exercise " + updatedExercise.getId().id() + " cannot be found in workout " + this.id))
                .update(updatedExercise);
    }

    public void deleteExercise(ExerciseId exerciseId) {
        Exercise exerciseToRemove = null;
        for (Exercise exercise : exercises) {
            if (exerciseToRemove != null) {
                exercise.decrementIndex();
            }
            if (exercise.getId().equals(exerciseId)) {
                exerciseToRemove = exercise;
            }
        }
        exercises.remove(exerciseToRemove);
    }

    private void validateExerciseToAddOrUpdate(Exercise exerciseToValidate) {
        validateIfContainsDuplicates(exerciseToValidate.getSets(), ExerciseSet::getIndex);
        for (Exercise e : exercises) {
            if (e.getId().equals(exerciseToValidate.getId())) {
                continue;
            }
            if (e.getExerciseType() == exerciseToValidate.getExerciseType()) {
                throw new IllegalStateException("Exercises within workout can't have duplicate types, failed update of workout " + this.id);
            }
            if (e.getIndex() == exerciseToValidate.getIndex()) {
                throw new IllegalStateException("Exercises within workout can't have duplicate indexes, failed update of workout " + this.id);
            }
        }
    }

    private Optional<Exercise> findExerciseById(ExerciseId exerciseId) {
        return exercises.stream()
                .filter(e -> e.getId().equals(exerciseId))
                .findFirst();
    }

    private <T, U> void validateIfContainsDuplicates(List<T> elements, Function<T, U> mapper) {
        List<U> mappedElements = elements.stream().map(mapper).toList();
        HashSet<U> mappedDistinctElements = new HashSet<>(mappedElements);
        if (mappedDistinctElements.size() < mappedElements.size()) {
            throw new IllegalStateException("Duplicate index found while trying to update exercises: " + elements);
        }
    }
}
