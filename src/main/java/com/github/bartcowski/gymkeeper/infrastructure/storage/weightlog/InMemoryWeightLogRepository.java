package com.github.bartcowski.gymkeeper.infrastructure.storage.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLog;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile("test")
public class InMemoryWeightLogRepository implements WeightLogRepository {

    private final Map<WeightLogId, WeightLog> weightLogsMap = new HashMap<>();

    @Override
    public List<WeightLog> findAllUsersWeightLogs(UserId userId) {
        return weightLogsMap.values()
                .stream()
                .filter(weightLog -> weightLog.userId().equals(userId))
                .toList();
    }

    @Override
    public Optional<WeightLog> findWeightLogById(WeightLogId weightLogId) {
        return Optional.ofNullable(weightLogsMap.get(weightLogId));
    }

    @Override
    public WeightLog addWeightLog(WeightLog weightLog) {
        weightLogsMap.put(weightLog.id(), weightLog);
        return weightLog;
    }

    @Override
    public void deleteWeightLog(WeightLogId weightLogId) {
        weightLogsMap.remove(weightLogId);
    }
}
