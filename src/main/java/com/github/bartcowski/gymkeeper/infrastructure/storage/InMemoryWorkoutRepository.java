package com.github.bartcowski.gymkeeper.infrastructure.storage;

import com.github.bartcowski.gymkeeper.app.WorkoutRepository;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.workout.Workout;
import com.github.bartcowski.gymkeeper.domain.workout.WorkoutId;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InMemoryWorkoutRepository implements WorkoutRepository {

    private final Map<WorkoutId, Workout> workoutsMap = new HashMap<>();

    @Override
    public List<Workout> findAllUsersWorkouts(UserId userId) {
        return workoutsMap.values()
                .stream()
                .filter(workout -> workout.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Optional<Workout> findWorkoutById(WorkoutId workoutId) {
        return Optional.ofNullable(workoutsMap.get(workoutId));
    }

    @Override
    public void addWorkout(Workout workout) {
        workoutsMap.put(workout.getId(), workout);
    }

    @Override
    public void deleteWorkout(WorkoutId workoutId) {
        workoutsMap.remove(workoutId);
    }

}
