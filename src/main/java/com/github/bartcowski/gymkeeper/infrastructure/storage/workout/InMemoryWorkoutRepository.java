package com.github.bartcowski.gymkeeper.infrastructure.storage.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.workout.Workout;
import com.github.bartcowski.gymkeeper.domain.workout.WorkoutId;
import com.github.bartcowski.gymkeeper.domain.workout.WorkoutRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile("test")
public class InMemoryWorkoutRepository implements WorkoutRepository {

    private final Map<WorkoutId, Workout> workoutsMap = new HashMap<>();

    @Override
    public List<Workout> findAllUsersWorkouts(UserId userId) {
        return workoutsMap.values()
                .stream()
                .filter(workout -> workout.userId().equals(userId))
                .toList();
    }

    @Override
    public Optional<Workout> findWorkoutById(WorkoutId workoutId) {
        return Optional.ofNullable(workoutsMap.get(workoutId));
    }

    @Override
    public Workout addWorkout(Workout workout) {
        workoutsMap.put(workout.id(), workout);
        return workout;
    }

    @Override
    public void deleteWorkout(WorkoutId workoutId) {
        workoutsMap.remove(workoutId);
    }
}
