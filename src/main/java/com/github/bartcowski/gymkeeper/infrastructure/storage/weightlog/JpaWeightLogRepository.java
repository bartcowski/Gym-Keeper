package com.github.bartcowski.gymkeeper.infrastructure.storage.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLog;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("prod")
interface JpaWeightLogRepository extends JpaRepository<WeightLog, WeightLogId> {

    List<WeightLog> findAllByUserId(UserId userId);
}
