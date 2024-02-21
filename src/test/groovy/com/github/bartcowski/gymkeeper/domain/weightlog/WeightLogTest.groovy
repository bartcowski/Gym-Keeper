package com.github.bartcowski.gymkeeper.domain.weightlog

import com.github.bartcowski.gymkeeper.domain.user.UserId
import com.github.bartcowski.gymkeeper.domain.user.UserWeight
import spock.lang.Specification

import java.time.LocalDate

class WeightLogTest extends Specification {

    def "should return weight log end date which is the date of the last entry in it"() {
        given:
        def weightLog = createWeightLogWithEntries([
                new WeightLogEntry(new UserWeight(86.4), LocalDate.of(2023, 1, 9)),
                new WeightLogEntry(new UserWeight(86.7), LocalDate.of(2023, 1, 2)),
                new WeightLogEntry(new UserWeight(86.6), LocalDate.of(2023, 1, 4))
        ])

        when:
        def endDate = weightLog.getEndDate()

        then:
        endDate == LocalDate.of(2023, 1, 9)
    }

    def "should properly add new entry"() {
        given:
        def date = LocalDate.of(2023, 1, 2)
        def weightLog = createWeightLogWithEntries([])
        def command = new CreateWeightLogEntryCommand(new UserWeight(86.7), date)

        when:
        weightLog.addNewEntry(command)

        then:
        weightLog.entries.size() == 1
        weightLog.entries.get(0).date() == date
    }

    def "should delete entry when given valid date"() {
        given:
        def date = LocalDate.of(2023, 1, 2)
        def existingLogEntry = new WeightLogEntry(new UserWeight(80.1), date)
        def weightLog = createWeightLogWithEntries([existingLogEntry])

        when:
        weightLog.deleteEntryFromDay(date)

        then:
        weightLog.entries.size() == 0
    }

    def "should do nothing when trying to delete entry from a day that does not have any entry"() {
        given:
        def date = LocalDate.of(2023, 1, 2)
        def existingLogEntry = new WeightLogEntry(new UserWeight(80.1), date)
        def weightLog = createWeightLogWithEntries([existingLogEntry])
        def invalidDate = LocalDate.of(2999, 10, 10)

        when:
        weightLog.deleteEntryFromDay(invalidDate)

        then:
        weightLog.entries.size() == 1
        weightLog.entries.contains(existingLogEntry)
    }

    def "should not add new entry and throw exception when #reason"() {
        given:
        def existingLogEntry = new WeightLogEntry(new UserWeight(80.1), LocalDate.of(2023, 1, 2))
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
