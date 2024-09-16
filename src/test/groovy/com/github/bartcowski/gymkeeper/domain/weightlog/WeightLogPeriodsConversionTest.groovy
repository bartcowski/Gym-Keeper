package com.github.bartcowski.gymkeeper.domain.weightlog

import com.github.bartcowski.gymkeeper.domain.user.UserId
import com.github.bartcowski.gymkeeper.domain.user.UserWeight
import spock.lang.Specification

import java.math.RoundingMode
import java.time.LocalDate

class WeightLogPeriodsConversionTest extends Specification {

    //TODO: improve test, add more cases
    def "should convert whole work log to 3 days periods"() {
        given:
        def weightLog = createWeightLogWithEntries([
                // 1st period
                new WeightLogEntry(new UserWeight(85.0), LocalDate.of(2023, 1, 2)),
                new WeightLogEntry(new UserWeight(86.0), LocalDate.of(2023, 1, 3)),
                // 2nd period
                new WeightLogEntry(new UserWeight(90.0), LocalDate.of(2023, 1, 4)),
                new WeightLogEntry(new UserWeight(90.0), LocalDate.of(2023, 1, 5)),
                // 3rd period
                new WeightLogEntry(new UserWeight(81.3), LocalDate.of(2023, 1, 7)),
                new WeightLogEntry(new UserWeight(82.9), LocalDate.of(2023, 1, 8)),
                new WeightLogEntry(new UserWeight(81.7), LocalDate.of(2023, 1, 9))
        ])

        when:
        def periods = weightLog.convertToPeriods(3)

        then:
        periods.size() == 3
        periods.get(0).endDate().isBefore(periods.get(1).startDate())
        periods.get(1).endDate().isBefore(periods.get(2).startDate())

        periods.get(0).startDate() == LocalDate.of(2023, 1, 1)
        periods.get(0).endDate() == LocalDate.of(2023, 1, 3)
        periods.get(1).startDate() == LocalDate.of(2023, 1, 4)
        periods.get(1).endDate() == LocalDate.of(2023, 1, 6)
        periods.get(2).startDate() == LocalDate.of(2023, 1, 7)
        periods.get(2).endDate() == LocalDate.of(2023, 1, 9)

        new BigDecimal(periods.get(0).averageWeight()).setScale(2, RoundingMode.HALF_UP).doubleValue() == 85.5d
        new BigDecimal(periods.get(1).averageWeight()).setScale(2, RoundingMode.HALF_UP).doubleValue() == 90.0d
        new BigDecimal(periods.get(2).averageWeight()).setScale(2, RoundingMode.HALF_UP).doubleValue() == 81.97d
    }

    private static WeightLog createWeightLogWithEntries(List<WeightLogEntry> entries) {
        return new WeightLog(
                new WeightLogId(1),
                new UserId(10),
                new WeightLogName("test weight log"),
                LocalDate.of(2023, 1, 1),
                entries)
    }
}
