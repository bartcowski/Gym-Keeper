package com.github.bartcowski.gymkeeper.infrastructure.web.weightlog;

import com.github.bartcowski.gymkeeper.app.weightlog.*;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogCommand;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogEntryCommand;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogName;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weightlog")
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

    @PostMapping("/{weightLogId}/entries")
    public ResponseEntity<Void> createWeightLogEntry(@PathVariable long weightLogId, @RequestBody CreateWeightLogEntryCommandDTO commandDTO) {
        CreateWeightLogEntryCommand command = commandDTO.toDomain();
        weightLogService.addWeightLogEntry(command, new WeightLogId(weightLogId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{weightLogId}/entries")
    public ResponseEntity<Void> deleteWeightLogEntry(@PathVariable long weightLogId, @RequestBody DeleteWeightLogEntryDTO deleteEntryDTO) {
        weightLogService.deleteWeightLogEntry(deleteEntryDTO.date, new WeightLogId(weightLogId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{weightLogId}")
    public ResponseEntity<WeightLogDTO> renameWeightLog(@PathVariable long weightLogId, @RequestBody RenameWeightLogDTO renameWeightLogDTO) {
        WeightLogDTO weightLogDTO = weightLogService.renameWeightLog(
                new WeightLogName(renameWeightLogDTO.name),
                new WeightLogId(weightLogId)
        );
        return ResponseEntity.ok(weightLogDTO);
    }

    @PostMapping
    public ResponseEntity<WeightLogDTO> createWeightLog(@RequestBody CreateWeightLogDTO createWeightLogDTO) {
        CreateWeightLogCommand command = createWeightLogDTO.toDomain();
        WeightLogDTO weightLogDTO = weightLogService.addWeightLog(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(weightLogDTO);
    }

    @DeleteMapping("/{weightLogId}")
    public ResponseEntity<Void> deleteWeightLog(@PathVariable long weightLogId) {
        weightLogService.deleteWeightLog(new WeightLogId(weightLogId));
        return ResponseEntity.ok().build();
    }

}
