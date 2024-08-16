package com.github.bartcowski.gymkeeper.app.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.workout.CreateWorkoutCommand;

import java.time.LocalDate;

public class CreateWorkoutDTO {

    public long userId;
    public LocalDate date;
    public boolean isDeload;
    public String comment;

    public CreateWorkoutCommand toDomain() {
        return new CreateWorkoutCommand(
                new UserId(userId),
                date,
                isDeload,
                comment
        );
    }
}
