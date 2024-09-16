package com.github.bartcowski.gymkeeper.app.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.workout.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Transactional(readOnly = true)
    public List<WorkoutDTO> findAllUsersWorkouts(UserId userId) {
        return workoutRepository.findAllUsersWorkouts(userId)
                .stream()
                .map(WorkoutDTO::fromDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public WorkoutDTO findWorkoutById(WorkoutId workoutId) {
        Workout workout = workoutRepository.findWorkoutById(workoutId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to update workout because no workout of id: " + workoutId + " can be found"));
        return WorkoutDTO.fromDomain(workout);
    }

    @Transactional
    public WorkoutDTO addWorkout(CreateWorkoutCommand command) {
        Workout createdWorkout = workoutRepository.addWorkout(command);
        return WorkoutDTO.fromDomain(createdWorkout);
    }

    @Transactional
    public WorkoutDTO updateWorkout(UpdateWorkoutCommand command, WorkoutId workoutId) {
        Workout workout = workoutRepository.findWorkoutById(workoutId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to update workout because no workout of id: " + workoutId + " can be found"));
        workout.updateWorkout(command);
        return WorkoutDTO.fromDomain(workout);
    }

    @Transactional
    public void deleteWorkout(WorkoutId workoutId) {
        workoutRepository.deleteWorkout(workoutId);
    }

    @Transactional
    public WorkoutDTO addExercise(CreateExerciseCommand command, WorkoutId workoutId) {
        ExerciseId newExerciseId = new ExerciseId(workoutRepository.nextIdentity());
        Workout workout = workoutRepository.findWorkoutById(workoutId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to update workout because no workout of id: " + workoutId + " can be found"));

        workout.addExercise(new Exercise(newExerciseId, command.index(), command.exerciseType(), command.sets(), command.comment()));
        return WorkoutDTO.fromDomain(workout);
    }

    @Transactional
    public WorkoutDTO updateExercise(Exercise updatedExercise, WorkoutId workoutId) {
        Workout workout = workoutRepository.findWorkoutById(workoutId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to update workout's exercise because no workout of id: " + workoutId.id() + " can be found"));
        workout.updateExercise(updatedExercise);
        return WorkoutDTO.fromDomain(workout);
    }

    @Transactional
    public WorkoutDTO deleteExercise(ExerciseId exerciseId, WorkoutId workoutId) {
        Workout workout = workoutRepository.findWorkoutById(workoutId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to delete workout's exercise because no workout of id: " + workoutId.id() + " can be found"));
        workout.deleteExercise(exerciseId);
        return WorkoutDTO.fromDomain(workout);
    }
}
