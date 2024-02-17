package com.github.bartcowski.gymkeeper.app.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.workout.Workout;
import com.github.bartcowski.gymkeeper.domain.workout.WorkoutId;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository {

    List<Workout> findAllUsersWorkouts(UserId userId);

    Optional<Workout> findWorkoutById(WorkoutId workoutId);

    void addWorkout(Workout workout);

    void deleteWorkout(WorkoutId workoutId);

}
