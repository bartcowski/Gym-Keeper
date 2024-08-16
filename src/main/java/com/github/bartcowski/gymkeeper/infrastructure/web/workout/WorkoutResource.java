package com.github.bartcowski.gymkeeper.infrastructure.web.workout;

import com.github.bartcowski.gymkeeper.app.workout.CreateWorkoutDTO;
import com.github.bartcowski.gymkeeper.app.workout.ExerciseDTO;
import com.github.bartcowski.gymkeeper.app.workout.WorkoutDTO;
import com.github.bartcowski.gymkeeper.app.workout.WorkoutService;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.workout.CreateExerciseCommand;
import com.github.bartcowski.gymkeeper.domain.workout.ExerciseId;
import com.github.bartcowski.gymkeeper.domain.workout.UpdateWorkoutCommand;
import com.github.bartcowski.gymkeeper.domain.workout.WorkoutId;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@AllArgsConstructor
public class WorkoutResource {

    private final WorkoutService workoutService;

    @GetMapping("/search")
    public ResponseEntity<List<WorkoutDTO>> getAllWorkoutsForUser(@RequestParam long userId) {
        List<WorkoutDTO> workoutDTOs = workoutService.findAllUsersWorkouts(new UserId(userId));
        return ResponseEntity.ok(workoutDTOs);
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutDTO> getWorkoutById(@PathVariable long workoutId) {
        WorkoutDTO workoutDTO = workoutService.findWorkoutById(new WorkoutId(workoutId));
        return ResponseEntity.ok(workoutDTO);
    }

    @PostMapping
    public ResponseEntity<WorkoutDTO> createWorkout(@RequestBody CreateWorkoutDTO createWorkoutDTO) {
        WorkoutDTO workoutDTO = workoutService.addWorkout(createWorkoutDTO.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutDTO);
    }

    @PutMapping("/{workoutId}")
    public ResponseEntity<WorkoutDTO> updateWorkout(@RequestBody UpdateWorkoutCommand command, @PathVariable long workoutId) {
        WorkoutDTO workoutDTO = workoutService.updateWorkout(command, new WorkoutId(workoutId));
        return ResponseEntity.ok(workoutDTO);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable long workoutId) {
        workoutService.deleteWorkout(new WorkoutId(workoutId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{workoutId}/exercises")
    public ResponseEntity<WorkoutDTO> createExercise(@RequestBody CreateExerciseCommand command, @PathVariable long workoutId) {
        WorkoutDTO workoutDTO = workoutService.addExercise(command, new WorkoutId(workoutId));
        return ResponseEntity.ok(workoutDTO);
    }

    @PutMapping("/{workoutId}/exercises")
    public ResponseEntity<WorkoutDTO> updateExercise(@RequestBody ExerciseDTO exerciseDTO, @PathVariable long workoutId) {
        WorkoutDTO workoutDTO = workoutService.updateExercise(exerciseDTO.toDomain(), new WorkoutId(workoutId));
        return ResponseEntity.ok(workoutDTO);
    }

    @DeleteMapping("/{workoutId}/exercises/{exerciseId}")
    public ResponseEntity<WorkoutDTO> deleteExercise(@PathVariable long exerciseId, @PathVariable long workoutId) {
        WorkoutDTO workoutDTO = workoutService.deleteExercise(new ExerciseId(exerciseId), new WorkoutId(workoutId));
        return ResponseEntity.ok(workoutDTO);
    }
}
