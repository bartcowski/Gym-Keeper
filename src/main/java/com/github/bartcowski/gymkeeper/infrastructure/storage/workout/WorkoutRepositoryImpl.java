package com.github.bartcowski.gymkeeper.infrastructure.storage.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.workout.Workout;
import com.github.bartcowski.gymkeeper.domain.workout.WorkoutId;
import com.github.bartcowski.gymkeeper.domain.workout.WorkoutRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("prod")
public class WorkoutRepositoryImpl implements WorkoutRepository {

    private final JpaWorkoutRepository jpaWorkoutRepository;

    public WorkoutRepositoryImpl(JpaWorkoutRepository jpaWorkoutRepository) {
        this.jpaWorkoutRepository = jpaWorkoutRepository;
    }

    @Override
    public List<Workout> findAllUsersWorkouts(UserId userId) {
        return jpaWorkoutRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Workout> findWorkoutById(WorkoutId workoutId) {
        return jpaWorkoutRepository.findById(workoutId);
    }

    @Override
    public Workout addWorkout(Workout workout) {
        return jpaWorkoutRepository.save(workout);
    }

    @Override
    public void deleteWorkout(WorkoutId workoutId) {
        jpaWorkoutRepository.deleteById(workoutId);
    }
}
