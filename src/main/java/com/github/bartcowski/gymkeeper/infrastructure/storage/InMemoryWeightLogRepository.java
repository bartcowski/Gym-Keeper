package com.github.bartcowski.gymkeeper.infrastructure.storage;

import com.github.bartcowski.gymkeeper.app.WeightLogRepository;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogCommand;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLog;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogId;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InMemoryWeightLogRepository implements WeightLogRepository {

    private static long weightLogIdCounter = 0;

    private final Map<WeightLogId, WeightLog> weightLogsMap = new HashMap<>();

    @Override
    public List<WeightLog> findAllUsersWeightLogs(UserId userId) {
        return weightLogsMap.values()
                .stream()
                .filter(weightLog -> weightLog.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Optional<WeightLog> findWeightLogById(WeightLogId weightLogId) {
        return Optional.ofNullable(weightLogsMap.get(weightLogId));
    }

    @Override
    public WeightLog addWeightLog(CreateWeightLogCommand command) {
        WeightLog newWeightLog = new WeightLog(
                new WeightLogId(weightLogIdCounter),
                command.userId(),
                command.name(),
                command.startDate()
        );
        weightLogsMap.put(newWeightLog.getId(), newWeightLog);
        weightLogIdCounter++;
        return newWeightLog;
    }

    @Override
    public void deleteWeightLog(WeightLogId weightLogId) {
        weightLogsMap.remove(weightLogId);
    }

}
