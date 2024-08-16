package com.github.bartcowski.gymkeeper.domain.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;

import java.time.LocalDate;

public record CreateWorkoutCommand(
        UserId userId,
        LocalDate date,
        boolean isDeload,
        String comment) {
}
