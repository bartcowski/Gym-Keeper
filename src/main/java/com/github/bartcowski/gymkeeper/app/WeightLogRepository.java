package com.github.bartcowski.gymkeeper.app;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogCommand;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLog;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogId;

import java.util.List;
import java.util.Optional;

public interface WeightLogRepository {

    List<WeightLog> findAllUsersWeightLogs(UserId userId);

    Optional<WeightLog> findWeightLogById(WeightLogId weightLogId);

    void addWeightLog(CreateWeightLogCommand command);

    void deleteWeightLog(WeightLogId weightLogId);

    void updateWeightLog(WeightLog weightLog);

}
