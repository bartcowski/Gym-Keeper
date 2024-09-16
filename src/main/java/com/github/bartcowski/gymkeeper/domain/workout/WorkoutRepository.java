package com.github.bartcowski.gymkeeper.domain.workout;

import com.github.bartcowski.gymkeeper.domain.IdGeneratingRepository;
import com.github.bartcowski.gymkeeper.domain.user.UserId;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends IdGeneratingRepository {

    List<Workout> findAllUsersWorkouts(UserId userId);

    Optional<Workout> findWorkoutById(WorkoutId workoutId);

    Workout addWorkout(Workout workout);

    void deleteWorkout(WorkoutId workoutId);

}
