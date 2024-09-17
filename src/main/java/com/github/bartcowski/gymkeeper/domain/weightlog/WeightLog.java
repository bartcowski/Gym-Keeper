package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.event.WeightLogEntryAdded;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "weight_log")
public class WeightLog {

    @EmbeddedId
    private WeightLogId id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    private UserId userId;

    private LocalDate startDate;

    @ElementCollection
    @CollectionTable(name = "weight_log_entry", joinColumns = @JoinColumn(name = "weight_log_id"))
    private List<WeightLogEntry> entries;

    @Embedded
    private WeightLogName name;

    @Version
    private int version;

    public WeightLog(WeightLogId id, UserId userId, WeightLogName name, LocalDate startDate, List<WeightLogEntry> entries) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.startDate = startDate;
        this.entries = entries;
        this.version = 1;
    }

    public WeightLog(WeightLogId id, UserId userId, WeightLogName name, LocalDate startDate) {
        this(id, userId, name, startDate, new ArrayList<>());
    }

    protected WeightLog() {
        //persistence
    }

    public WeightLogId id() {
        return id;
    }

    public UserId userId() {
        return userId;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public List<WeightLogEntry> entries() {
        return entries;
    }

    public WeightLogName name() {
        return name;
    }

    public LocalDate getEndDate() {
        return entries.stream()
                .max(Comparator.comparing(WeightLogEntry::date))
                .map(WeightLogEntry::date)
                .orElse(startDate);
    }

    public void renameWeightLog(WeightLogName newName) {
        this.name = newName;
    }

    public WeightLogEntryAdded addNewEntry(WeightLogEntry newEntry) {
        if (entryForGivenDayAlreadyExists(newEntry.date())) {
            throw new IllegalStateException("Weight log entry for given day already exists!");
        }
        if (newEntryDateIsBeforeWeightLogStartDate(newEntry.date())) {
            throw new IllegalStateException("New weight log entry cannot be before weight log's start date!");
        }

        entries.add(newEntry);
        return new WeightLogEntryAdded(this.userId, newEntry.weight());
    }

    public void deleteEntryFromDay(LocalDate entryToDeleteDate) {
        entries.stream()
                .filter(entry -> entry.date().equals(entryToDeleteDate))
                .findFirst()
                .ifPresent(entries::remove);
    }

    public List<WeightLogPeriod> convertToPeriods(int periodLengthInDays) {
        if (periodLengthInDays <= 1) {
            throw new IllegalArgumentException("Period must be at least 2 days long");
        }

        List<WeightLogPeriod> periods = new ArrayList<>();
        List<WeightLogEntry> sortedEntries = entries.stream()
                .sorted(Comparator.comparing(WeightLogEntry::date))
                .toList();

        LocalDate lastDayOfCurrentPeriod = startDate.plusDays(periodLengthInDays - 1);
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
            } else if (i == sortedEntries.size() - 1) { //last incomplete period
                WeightLogPeriod period = new WeightLogPeriod(
                        sortedEntries.subList(firstIndexOfNextSublist, i + 1),
                        lastDayOfCurrentPeriod.minusDays(periodLengthInDays - 1),
                        lastDayOfCurrentPeriod);
                periods.add(period);
            }
        }
        return periods;
    }

    private boolean entryForGivenDayAlreadyExists(LocalDate date) {
        return entries.stream().anyMatch(existingEntry -> existingEntry.date().equals(date));
    }

    private boolean newEntryDateIsBeforeWeightLogStartDate(LocalDate date) {
        return date.isBefore(startDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightLog weightLog = (WeightLog) o;
        return Objects.equals(id, weightLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
