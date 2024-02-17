package com.github.bartcowski.gymkeeper.app.weightlog;

import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLog;

import java.time.LocalDate;
import java.util.List;

public class WeightLogDTO {

    public long id;

    public long userId;

    public LocalDate startDate;

    public List<WeightLogEntryDTO> entries;

    public String name;

    public WeightLogDTO(long id, long userId, LocalDate startDate, List<WeightLogEntryDTO> entries, String name) {
        this.id = id;
        this.userId = userId;
        this.startDate = startDate;
        this.entries = entries;
        this.name = name;
    }

    public static WeightLogDTO fromDomain(WeightLog domainWeightLog) {
        return new WeightLogDTO(
                domainWeightLog.getId().id(),
                domainWeightLog.getUserId().id(),
                domainWeightLog.getStartDate(),
                domainWeightLog.getEntries().stream().map(WeightLogEntryDTO::fromDomain).toList(),
                domainWeightLog.getName().name()
        );
    }

}
