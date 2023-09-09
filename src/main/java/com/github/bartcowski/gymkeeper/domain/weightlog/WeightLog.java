package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        this.entries = entries;
        //entries.forEach(this::addNewEntry); //TODO: validate entries on creation?
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

    //TODO: either add ID managing logic or (preferably) change newEntry into EntryCreateCommand
    public void addNewEntry(CreateWeightLogEntryCommand command) {
        if (entryForGivenDayAlreadyExists(command.date())) {
            throw new IllegalStateException("Weight log entry for given day already exists!");
        }
        if (newEntryDateIsBeforeWeightLogStartDate(command.date())) {
            throw new IllegalStateException("New weight log entry cannot be before weight log's start date!");
        }

        WeightLogEntryId newEntryId = createNewEntryId();
        WeightLogEntry newWeightLogEntry = new WeightLogEntry(newEntryId, command.weight(), command.date(), command.comment());
        entries.add(newWeightLogEntry);
    }

    public void deleteEntry(WeightLogEntryId weightLogEntryId) {
        Optional<WeightLogEntry> entryToDelete = entries.stream()
                .filter(entry -> entry.id().equals(weightLogEntryId))
                .findFirst();
        if (entryToDelete.isEmpty()) {
            return;
        }
        entries.remove(entryToDelete.get());
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

    private WeightLogEntryId createNewEntryId() {
        return entries.stream()
                .max(Comparator.comparing(e -> e.id().id()))
                .map(e -> new WeightLogEntryId(e.id().id() + 1))
                .orElseGet(() -> new WeightLogEntryId(0));
    }

    private boolean entryForGivenDayAlreadyExists(LocalDate date) {
        return entries.stream().anyMatch(existingEntry -> existingEntry.date().equals(date));
    }

    private boolean newEntryDateIsBeforeWeightLogStartDate(LocalDate date) {
        return date.isBefore(startDate);
    }

}
