package com.github.bartcowski.gymkeeper.app.weightlog;

import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogEntry;

import java.time.LocalDate;

public class WeightLogEntryDTO {

    public double weight;

    public LocalDate date;

    public String comment;

    public WeightLogEntryDTO(double weight, LocalDate date, String comment) {
        this.weight = weight;
        this.date = date;
        this.comment = comment;
    }

    public static WeightLogEntryDTO fromDomain(WeightLogEntry domainEntry) {
        return new WeightLogEntryDTO(
                domainEntry.weight().value(),
                domainEntry.date(),
                domainEntry.comment()
        );
    }
}
