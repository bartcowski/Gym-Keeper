package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserWeight;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class WeightLogPeriod {

    List<WeightLogEntry> entries;

    double averageWeight;

    LocalDate startDate;

    LocalDate endDate;

    public WeightLogPeriod(List<WeightLogEntry> entries, LocalDate startDate, LocalDate endDate) {
        this.entries = entries;
        this.startDate = startDate;
        this.endDate = endDate;
        averageWeight = entries
                .stream()
                .map(WeightLogEntry::weight)
                .mapToDouble(UserWeight::value)
                .sum() / entries.size();
    }
}
