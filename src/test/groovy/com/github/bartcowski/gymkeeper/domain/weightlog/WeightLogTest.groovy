package com.github.bartcowski.gymkeeper.domain.weightlog

import com.github.bartcowski.gymkeeper.domain.user.UserId
import com.github.bartcowski.gymkeeper.domain.user.UserWeight
import spock.lang.Specification

import java.time.LocalDate
import java.util.stream.Collectors

class WeightLogTest extends Specification {

    def "should return weight log end date which is the date of te last entry in it"() {
        given:
        def weightLog = createWeightLogWithEntries([
                new WeightLogEntry(new WeightLogEntryId(1), new UserWeight(86.4), LocalDate.of(2023, 1, 9)),
                new WeightLogEntry(new WeightLogEntryId(2), new UserWeight(86.7), LocalDate.of(2023, 1, 2)),
                new WeightLogEntry(new WeightLogEntryId(3), new UserWeight(86.6), LocalDate.of(2023, 1, 4))
        ])

        when:
        def endDate = weightLog.getEndDate()

        then:
        endDate == LocalDate.of(2023, 1, 9)
    }

    def "should properly add first new entry and assign ID = 0"() {
        given:
        def weightLog = createWeightLogWithEntries([])
        def command = new CreateWeightLogEntryCommand(new UserWeight(86.7), LocalDate.of(2023, 1, 2))

        when:
        weightLog.addNewEntry(command)

        then:
        weightLog.entries.size() == 1
        weightLog.entries.get(0).id() == new WeightLogEntryId(0)
    }

    def "should properly add entries when some already exist and assign IDs"() {
        given:
        def existingLogEntry = new WeightLogEntry(new WeightLogEntryId(10), new UserWeight(80.1), LocalDate.of(2023, 1, 2))
        def weightLog = createWeightLogWithEntries([existingLogEntry])
        def command1 = new CreateWeightLogEntryCommand(new UserWeight(86.7), LocalDate.of(2023, 1, 3))
        def command2 = new CreateWeightLogEntryCommand(new UserWeight(85.0), LocalDate.of(2023, 1, 4))

        when:
        weightLog.addNewEntry(command1)
        weightLog.addNewEntry(command2)

        then:
        weightLog.entries.size() == 3
        weightLog.entries.stream().map(WeightLogEntry::id).collect(Collectors.toList()).containsAll(
                new WeightLogEntryId(10), new WeightLogEntryId(11), new WeightLogEntryId(12)
        )
    }

    def "should delete entry when given valid id"() {
        given:
        def existingLogEntry = new WeightLogEntry(new WeightLogEntryId(10), new UserWeight(80.1), LocalDate.of(2023, 1, 2))
        def weightLog = createWeightLogWithEntries([existingLogEntry])

        when:
        weightLog.deleteEntry(new WeightLogEntryId(10))

        then:
        weightLog.entries.size() == 0
    }

    def "should do nothing when trying to delete entry of non-existing id"() {
        given:
        def existingLogEntry = new WeightLogEntry(new WeightLogEntryId(10), new UserWeight(80.1), LocalDate.of(2023, 1, 2))
        def weightLog = createWeightLogWithEntries([existingLogEntry])

        when:
        weightLog.deleteEntry(new WeightLogEntryId(999))

        then:
        weightLog.entries.size() == 1
        weightLog.entries.contains(existingLogEntry)
    }

    def "should not add new entry and throw exception when #reason"() {
        given:
        def existingLogEntry = new WeightLogEntry(new WeightLogEntryId(1), new UserWeight(80.1), LocalDate.of(2023, 1, 2))
        def weightLog = createWeightLogWithEntries([existingLogEntry])
        def command = new CreateWeightLogEntryCommand(new UserWeight(86.7), newLogEntryDate)

        when:
        weightLog.addNewEntry(command)

        then:
        thrown(IllegalStateException.class)
        weightLog.entries.size() == 1
        weightLog.entries.get(0) == existingLogEntry

        where:
        reason                                                          | newLogEntryDate
        "weight log entry for the same day already exists"              | LocalDate.of(2023, 1, 2)
        "new weight log entry is before the weight log's start date"    | LocalDate.of(2022, 12, 31)
    }

    private WeightLog createWeightLogWithEntries(List<WeightLogEntry> entries) {
        return new WeightLog(
                new WeightLogId(1),
                new UserId(10),
                new WeightLogName("test weight log"),
                LocalDate.of(2023, 1, 1),
                entries)
    }

}
