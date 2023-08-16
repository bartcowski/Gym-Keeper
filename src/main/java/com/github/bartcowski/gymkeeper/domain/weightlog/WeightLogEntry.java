package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserWeight;

import java.time.LocalDate;

public record WeightLogEntry(WeightLogEntryId id, UserWeight weight, LocalDate date, String comment) {

    public WeightLogEntry(WeightLogEntryId id, UserWeight weight, LocalDate date) {
        this(id, weight, date, "");
    }

}
