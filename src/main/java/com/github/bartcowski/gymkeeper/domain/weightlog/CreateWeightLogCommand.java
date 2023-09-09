package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserId;

import java.time.LocalDate;

public record CreateWeightLogCommand(
        UserId userId,
        WeightLogName name,
        LocalDate startDate
) {
}
