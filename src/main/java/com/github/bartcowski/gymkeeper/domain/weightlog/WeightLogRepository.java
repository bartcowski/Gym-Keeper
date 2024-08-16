package com.github.bartcowski.gymkeeper.domain.weightlog;

import com.github.bartcowski.gymkeeper.domain.IdGeneratingRepository;
import com.github.bartcowski.gymkeeper.domain.user.UserId;

import java.util.List;
import java.util.Optional;

public interface WeightLogRepository extends IdGeneratingRepository {

    List<WeightLog> findAllUsersWeightLogs(UserId userId);

    Optional<WeightLog> findWeightLogById(WeightLogId weightLogId);

    WeightLog addWeightLog(CreateWeightLogCommand command);

    void deleteWeightLog(WeightLogId weightLogId);

}
