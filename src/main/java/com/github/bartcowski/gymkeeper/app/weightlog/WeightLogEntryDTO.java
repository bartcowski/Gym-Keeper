package com.github.bartcowski.gymkeeper.app.weightlog;

import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogEntry;

import java.time.LocalDate;

class WeightLogEntryDTO {

    long id;

    double weight;

    LocalDate date;

    String comment;

    WeightLogEntryDTO(long id, double weight, LocalDate date, String comment) {
        this.id = id;
        this.weight = weight;
        this.date = date;
        this.comment = comment;
    }

    static WeightLogEntryDTO fromDomain(WeightLogEntry domainEntry) {
        return new WeightLogEntryDTO(
                domainEntry.id().id(),
                domainEntry.weight().value(),
                domainEntry.date(),
                domainEntry.comment()
        );
    }
}
