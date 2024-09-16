package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserWeight;

import java.time.LocalDate;
import java.util.List;

public class WeightLogPeriod {

    private final List<WeightLogEntry> entries;

    private final double averageWeight;

    private final LocalDate startDate;

    private final LocalDate endDate;

    public WeightLogPeriod(List<WeightLogEntry> entries, LocalDate startDate, LocalDate endDate) {
        this.entries = entries;
        this.startDate = startDate;
        this.endDate = endDate;
        this.averageWeight = entries
                .stream()
                .map(WeightLogEntry::weight)
                .mapToDouble(UserWeight::weight)
                .sum() / entries.size();
    }

    public List<WeightLogEntry> entries() {
        return entries;
    }

    public double averageWeight() {
        return averageWeight;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }
}
