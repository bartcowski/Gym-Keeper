package com.github.bartcowski.gymkeeper.app.weightlog;

import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogEntry;

import java.time.LocalDate;

class WeightLogEntryDTO {

    double weight;

    LocalDate date;

    String comment;

    WeightLogEntryDTO(double weight, LocalDate date, String comment) {
        this.weight = weight;
        this.date = date;
        this.comment = comment;
    }

    static WeightLogEntryDTO fromDomain(WeightLogEntry domainEntry) {
        return new WeightLogEntryDTO(
                domainEntry.weight().value(),
                domainEntry.date(),
                domainEntry.comment()
        );
    }
}
