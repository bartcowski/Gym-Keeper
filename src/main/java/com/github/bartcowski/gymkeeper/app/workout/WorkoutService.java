package com.github.bartcowski.gymkeeper.app.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.workout.Exercise;
import com.github.bartcowski.gymkeeper.domain.workout.Workout;
import com.github.bartcowski.gymkeeper.domain.workout.WorkoutId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    List<Workout> findAllUsersWorkouts(UserId userId) {
        return workoutRepository.findAllUsersWorkouts(userId);
    }

    Optional<Workout> findWorkoutById(WorkoutId workoutId) {
        return workoutRepository.findWorkoutById(workoutId);
    }

    void addWorkout(Workout workout) {
        workoutRepository.addWorkout(workout);
    }

    void deleteWorkout(WorkoutId workoutId) {
        workoutRepository.deleteWorkout(workoutId);
    }

    @Transactional
    void updateWorkoutExercises(List<Exercise> updatedExercises, WorkoutId workoutId) {
        Workout workout = workoutRepository.findWorkoutById(workoutId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to update workout's exercises because no workout of id: " + workoutId.id() + " can be found"));
        workout.updateExercises(updatedExercises);
    }
}
