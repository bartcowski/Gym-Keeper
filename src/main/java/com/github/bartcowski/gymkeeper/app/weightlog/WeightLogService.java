package com.github.bartcowski.gymkeeper.app.weightlog;

import com.github.bartcowski.gymkeeper.domain.event.DomainEventPublisher;
import com.github.bartcowski.gymkeeper.domain.event.WeightLogEntryAdded;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.weightlog.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeightLogService {

    private final WeightLogRepository weightLogRepository;

    private final DomainEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public List<WeightLogDTO> findAllUsersWeightLogs(UserId userId) {
        return weightLogRepository.findAllUsersWeightLogs(userId)
                .stream()
                .map(WeightLogDTO::fromDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<WeightLogDTO> findWeightLogById(WeightLogId weightLogId) {
        return weightLogRepository.findWeightLogById(weightLogId).map(WeightLogDTO::fromDomain);
    }

    @Transactional
    public WeightLogDTO addWeightLog(CreateWeightLogCommand command) {
        WeightLog weightLog = weightLogRepository.addWeightLog(command);
        return WeightLogDTO.fromDomain(weightLog);
    }

    @Transactional
    public void deleteWeightLog(WeightLogId weightLogId) {
        weightLogRepository.deleteWeightLog(weightLogId);
    }

    @Transactional(readOnly = true)
    public List<WeightLogPeriodDTO> getWeightLogPeriods(WeightLogId weightLogId, int periodLengthInDays) {
        WeightLog weightLog = weightLogRepository.findWeightLogById(weightLogId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to get weight log periods because no weight log of id: " + weightLogId.id() + " can be found"));
        return weightLog.convertToPeriods(periodLengthInDays)
                .stream()
                .map(WeightLogPeriodDTO::fromDomain)
                .toList();
    }

    @Transactional
    public WeightLogDTO addWeightLogEntry(CreateWeightLogEntryCommand command, WeightLogId weightLogId) {
        WeightLog weightLog = weightLogRepository.findWeightLogById(weightLogId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to add new weight log entry because no corresponding weight log of id: " + weightLogId.id() + " can be found"));
        WeightLogEntryAdded weightLogEntryAdded = weightLog.addNewEntry(command);
        eventPublisher.publish(weightLogEntryAdded);
        return WeightLogDTO.fromDomain(weightLog);
    }

    @Transactional
    public void deleteWeightLogEntry(LocalDate entryToDeleteDate, WeightLogId weightLogId) {
        WeightLog weightLog = weightLogRepository.findWeightLogById(weightLogId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to delete weight log entry because no corresponding weight log of id: " + weightLogId.id() + " can be found"));
        weightLog.deleteEntryFromDay(entryToDeleteDate);
    }

    @Transactional
    public WeightLogDTO renameWeightLog(WeightLogName weightLogName, WeightLogId weightLogId) {
        WeightLog weightLog = weightLogRepository.findWeightLogById(weightLogId)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to rename weight log because no weight log of id: " + weightLogId.id() + " can be found"));
        weightLog.renameWeightLog(weightLogName);
        return WeightLogDTO.fromDomain(weightLog);
    }

}
