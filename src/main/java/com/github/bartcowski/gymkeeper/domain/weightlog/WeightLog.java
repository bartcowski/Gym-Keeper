package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import lombok.Value;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Value
public class WeightLog {

    WeightLogId id;

    UserId userId; //WeightLog creator, loose coupling between User and Workout

    WeightLogName name;

    LocalDate startDate;

    List<WeightLogEntry> entries;

    public LocalDate getEndDate() {
        return entries.stream()
                .max(Comparator.comparing(WeightLogEntry::date))
                .map(WeightLogEntry::date)
                .orElse(startDate);
    }

    public WeightLog renameWeightLog(WeightLogName newName) {
        return new WeightLog(id, userId, newName, startDate, entries);
    }

    public void addNewEntry(WeightLogEntry newEntry) {
        if (entryForGivenDayAlreadyExists(newEntry)) {
            throw new IllegalStateException("Weight log entry for given day already exists!");
        }
        if (newEntryDateIsBeforeWeightLogStartDate(newEntry)) {
            throw new IllegalStateException("New weight log entry cannot be before weight log's start date!");
        }
        entries.add(newEntry);
    }

    public List<WeightLogPeriod> convertToPeriods(int periodLengthInDays) {
        return null;
    }

    private boolean entryForGivenDayAlreadyExists(WeightLogEntry entry) {
        return entries.stream().anyMatch(existingEntry -> existingEntry.date().equals(entry.date()));
    }

    private boolean newEntryDateIsBeforeWeightLogStartDate(WeightLogEntry entry) {
        return entry.date().isBefore(startDate);
    }

}
