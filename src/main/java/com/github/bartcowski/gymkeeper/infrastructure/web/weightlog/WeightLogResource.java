package com.github.bartcowski.gymkeeper.infrastructure.web.weightlog;

import com.github.bartcowski.gymkeeper.app.weightlog.*;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogCommand;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogName;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weightlogs")
@AllArgsConstructor
public class WeightLogResource {

    private final WeightLogService weightLogService;

    @GetMapping("/search")
    public ResponseEntity<List<WeightLogDTO>> getAllUsersWeightLogs(@RequestParam long userId) {
        List<WeightLogDTO> weightLogDTOs = weightLogService.findAllUsersWeightLogs(new UserId(userId));
        return ResponseEntity.ok(weightLogDTOs);
    }

    @GetMapping("/{weightLogId}")
    public ResponseEntity<WeightLogDTO> getWeightLogById(@PathVariable long weightLogId) {
        Optional<WeightLogDTO> weightLogOptional = weightLogService.findWeightLogById(new WeightLogId(weightLogId));

        if (weightLogOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        WeightLogDTO weightLogDTO = weightLogOptional.get();
        return ResponseEntity.ok(weightLogDTO);
    }

    @GetMapping("/{weightLogId}/periods")
    public ResponseEntity<List<WeightLogPeriodDTO>> getWeightLogPeriods(@PathVariable long weightLogId, @RequestParam int length) {
        if (length <= 1) {
            length = 7;
        }
        List<WeightLogPeriodDTO> weightLogPeriodDTOs = weightLogService.getWeightLogPeriods(new WeightLogId(weightLogId), length);
        return ResponseEntity.ok(weightLogPeriodDTOs);
    }

    @PostMapping
    public ResponseEntity<WeightLogDTO> createWeightLog(@RequestBody CreateWeightLogCommandDTO createWeightLogCommandDTO) {
        CreateWeightLogCommand command = createWeightLogCommandDTO.toDomain();
        WeightLogDTO weightLogDTO = weightLogService.addWeightLog(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(weightLogDTO);
    }

    @PutMapping("/{weightLogId}")
    public ResponseEntity<WeightLogDTO> renameWeightLog(@PathVariable long weightLogId, @RequestBody WeightLogNameDTO weightLogNameDTO) {
        WeightLogDTO weightLogDTO = weightLogService.renameWeightLog(
                new WeightLogName(weightLogNameDTO.name),
                new WeightLogId(weightLogId)
        );
        return ResponseEntity.ok(weightLogDTO);
    }

    @DeleteMapping("/{weightLogId}")
    public ResponseEntity<Void> deleteWeightLog(@PathVariable long weightLogId) {
        weightLogService.deleteWeightLog(new WeightLogId(weightLogId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{weightLogId}/entries")
    public ResponseEntity<WeightLogDTO> createWeightLogEntry(@PathVariable long weightLogId, @RequestBody WeightLogEntryDTO weightLogEntryDTO) {
        WeightLogDTO weightLogDTO = weightLogService.addWeightLogEntry(weightLogEntryDTO.toDomain(), new WeightLogId(weightLogId));
        return ResponseEntity.status(HttpStatus.CREATED).body(weightLogDTO);
    }

    @DeleteMapping("/{weightLogId}/entries")
    public ResponseEntity<Void> deleteWeightLogEntry(@PathVariable long weightLogId, @RequestBody WeightLogEntryDateDTO weightLogEntryDateDTO) {
        weightLogService.deleteWeightLogEntry(weightLogEntryDateDTO.date, new WeightLogId(weightLogId));
        return ResponseEntity.ok().build();
    }
}
