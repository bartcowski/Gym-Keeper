package com.github.bartcowski.gymkeeper.app.weightlog;

import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogPeriod;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class WeightLogPeriodDTO {

    List<WeightLogEntryDTO> entries;

    double averageWeight;

    LocalDate startDate;

    LocalDate endDate;

    public WeightLogPeriodDTO(List<WeightLogEntryDTO> entries, double averageWeight, LocalDate startDate, LocalDate endDate) {
        this.entries = entries;
        this.averageWeight = averageWeight;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static WeightLogPeriodDTO fromDomain(WeightLogPeriod weightLogPeriod) {
        return new WeightLogPeriodDTO(
                weightLogPeriod.entries()
                        .stream()
                        .map(WeightLogEntryDTO::fromDomain)
                        .collect(Collectors.toList()),
                weightLogPeriod.averageWeight(),
                weightLogPeriod.startDate(),
                weightLogPeriod.endDate()
        );
    }

}
