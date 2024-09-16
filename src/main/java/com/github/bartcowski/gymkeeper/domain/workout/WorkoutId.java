package com.github.bartcowski.gymkeeper.domain.workout;

import jakarta.persistence.Embeddable;

@Embeddable
public record WorkoutId(long id) {
}
