package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserWeight;

import java.time.LocalDate;

public record WeightLogEntry(
        UserWeight weight,
        LocalDate date,
        String comment) {

    public WeightLogEntry(UserWeight weight, LocalDate date) {
        this(weight, date, "");
    }

}
