package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserWeight;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public record WeightLogEntry(
        UserWeight weight,
        LocalDate date,
        String comment) {

    public WeightLogEntry(UserWeight weight, LocalDate date) {
        this(weight, date, "");
    }
}
