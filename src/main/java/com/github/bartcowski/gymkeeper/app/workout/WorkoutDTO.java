package com.github.bartcowski.gymkeeper.app.workout;

import com.github.bartcowski.gymkeeper.domain.workout.Workout;

import java.time.LocalDate;
import java.util.List;

public class WorkoutDTO {

    public long id;
    public long userId;
    public List<ExerciseDTO> exercises;
    public LocalDate date;
    public boolean isDeload;
    public String comment;

    public WorkoutDTO(long id, long userId, List<ExerciseDTO> exercises, LocalDate date, boolean isDeload, String comment) {
        this.id = id;
        this.userId = userId;
        this.exercises = exercises;
        this.date = date;
        this.isDeload = isDeload;
        this.comment = comment;
    }

    public static WorkoutDTO fromDomain(Workout workout) {
        return new WorkoutDTO(
                workout.getId().id(),
                workout.getUserId().id(),
                workout.getExercises().stream().map(ExerciseDTO::fromDomain).toList(),
                workout.getDate(),
                workout.isDeload(),
                workout.getComment()
        );
    }
}
