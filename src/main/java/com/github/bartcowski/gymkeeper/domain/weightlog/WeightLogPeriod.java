package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserWeight;
import lombok.Value;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Value
public class WeightLogPeriod {

    List<WeightLogEntry> entries;

    double averageWeight;

    LocalDate startDate;

    LocalDate endDate;

    public WeightLogPeriod(List<WeightLogEntry> entries) {
        this.entries = entries;
        //TODO: think of more efficient way to extract all data than 3 separate streams
        averageWeight = entries
                .stream()
                .map(WeightLogEntry::weight)
                .mapToDouble(UserWeight::value)
                .sum() / entries.size();
        startDate = entries.stream()
                .min(Comparator.comparing(WeightLogEntry::date))
                .map(WeightLogEntry::date)
                .orElse(LocalDate.EPOCH);
        endDate = entries.stream()
                .min(Comparator.comparing(WeightLogEntry::date))
                .map(WeightLogEntry::date)
                .orElse(LocalDate.EPOCH);
    }
}
