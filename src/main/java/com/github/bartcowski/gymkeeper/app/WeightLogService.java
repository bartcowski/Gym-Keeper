package com.github.bartcowski.gymkeeper.app;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLog;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogEntry;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogPeriod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeightLogService {

    private final WeightLogRepository weightLogRepository;

    public List<WeightLog> findAllUsersWeightLogs(UserId userId) {
        return weightLogRepository.findAllUsersWeightLogs(userId);
    }

    public Optional<WeightLog> findWeightLogById(WeightLogId weightLogId) {
        return weightLogRepository.findWeightLogById(weightLogId);
    }

    public void addWeightLog(WeightLog weightLog) {
        weightLogRepository.addWeightLog(weightLog);
    }

    public void deleteWeightLog(WeightLogId weightLogId) {
        weightLogRepository.deleteWeightLog(weightLogId);
    }

    public List<WeightLogPeriod> getWeightLogPeriods(WeightLogId weightLogId, int periodLengthInDays) {
        WeightLog weightLog = weightLogRepository.findWeightLogById(weightLogId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to get weight log periods because no weight log of id: " + weightLogId.id() + " can be found"));
        return weightLog.convertToPeriods(periodLengthInDays);
    }

    public void addWeightLogEntry(WeightLogEntry weightLogEntry, WeightLogId weightLogId) {
        WeightLog weightLog = weightLogRepository.findWeightLogById(weightLogId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to add new weight log entry because no corresponding weight log of id: " + weightLogId.id() + " can be found"));
        weightLog.addNewEntry(weightLogEntry);
        weightLogRepository.updateWeightLog(weightLog);
    }

}
