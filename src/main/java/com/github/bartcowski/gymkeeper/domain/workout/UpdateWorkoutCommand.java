package com.github.bartcowski.gymkeeper.domain.workout;

import java.time.LocalDate;

public record UpdateWorkoutCommand(
        LocalDate date,
        boolean isDeload,
        String comment) {
}
