package com.github.bartcowski.gymkeeper.infrastructure.storage.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLog;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("prod")
public class WeightLogRepositoryImpl implements WeightLogRepository {

    private final JpaWeightLogRepository jpaWeightLogRepository;

    WeightLogRepositoryImpl(JpaWeightLogRepository jpaWeightLogRepository) {
        this.jpaWeightLogRepository = jpaWeightLogRepository;
    }

    @Override
    public List<WeightLog> findAllUsersWeightLogs(UserId userId) {
        return jpaWeightLogRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<WeightLog> findWeightLogById(WeightLogId weightLogId) {
        return jpaWeightLogRepository.findById(weightLogId);
    }

    @Override
    public WeightLog addWeightLog(WeightLog weightLog) {
        return jpaWeightLogRepository.save(weightLog);
    }

    @Override
    public void deleteWeightLog(WeightLogId weightLogId) {
        jpaWeightLogRepository.deleteById(weightLogId);
    }
}
