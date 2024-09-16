package com.github.bartcowski.gymkeeper.infrastructure.storage.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.workout.Workout;
import com.github.bartcowski.gymkeeper.domain.workout.WorkoutId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("prod")
public interface JpaWorkoutRepository extends JpaRepository<Workout, WorkoutId> {

    List<Workout> findAllByUserId(UserId userId);
}
