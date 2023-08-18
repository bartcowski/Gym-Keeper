package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Value
public class WeightLog {

    WeightLogId id;

    UserId userId; //WeightLog creator, loose coupling between User and Workout

    WeightLogName name;

    LocalDate startDate;

    List<WeightLogEntry> entries;

    public WeightLog(WeightLogId id, UserId userId, WeightLogName name, LocalDate startDate, List<WeightLogEntry> entries) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.startDate = startDate;
        this.entries = new ArrayList<>();
        entries.forEach(this::addNewEntry); //TODO: more efficient way to validate all entries on creation?
    }

    public WeightLog(WeightLogId id, UserId userId, WeightLogName name, LocalDate startDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.startDate = startDate;
        this.entries = new ArrayList<>();
    }

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
        List<WeightLogPeriod> periods = new ArrayList<>();
        List<WeightLogEntry> sortedEntries = entries.stream()
                .sorted(Comparator.comparing(WeightLogEntry::date))
                .toList();

        LocalDate lastDayOfCurrentPeriod = startDate;

        lastDayOfCurrentPeriod = lastDayOfCurrentPeriod.plusDays(periodLengthInDays - 1);
        int firstIndexOfNextSublist = 0;
        for (int i = 0; i < sortedEntries.size(); ++i) {
            if (sortedEntries.get(i).date().equals(lastDayOfCurrentPeriod)) {
                WeightLogPeriod period = new WeightLogPeriod(
                        sortedEntries.subList(firstIndexOfNextSublist, i + 1),
                        lastDayOfCurrentPeriod.minusDays(periodLengthInDays - 1),
                        lastDayOfCurrentPeriod);
                periods.add(period);
                lastDayOfCurrentPeriod = lastDayOfCurrentPeriod.plusDays(periodLengthInDays);
                firstIndexOfNextSublist = i + 1;
            } else if (sortedEntries.get(i).date().isAfter(lastDayOfCurrentPeriod)) {
                WeightLogPeriod period = new WeightLogPeriod(
                        sortedEntries.subList(firstIndexOfNextSublist, i),
                        lastDayOfCurrentPeriod.minusDays(periodLengthInDays - 1),
                        lastDayOfCurrentPeriod);
                periods.add(period);
                lastDayOfCurrentPeriod = lastDayOfCurrentPeriod.plusDays(periodLengthInDays);
                firstIndexOfNextSublist = i;
            }
        }
        return periods;
    }

    private boolean entryForGivenDayAlreadyExists(WeightLogEntry entry) {
        return entries.stream().anyMatch(existingEntry -> existingEntry.date().equals(entry.date()));
    }

    private boolean newEntryDateIsBeforeWeightLogStartDate(WeightLogEntry entry) {
        return entry.date().isBefore(startDate);
    }

}
