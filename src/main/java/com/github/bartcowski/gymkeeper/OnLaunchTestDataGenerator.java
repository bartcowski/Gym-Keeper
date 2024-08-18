package com.github.bartcowski.gymkeeper;

import com.github.bartcowski.gymkeeper.app.user.UserService;
import com.github.bartcowski.gymkeeper.app.weightlog.WeightLogService;
import com.github.bartcowski.gymkeeper.app.workout.WorkoutService;
import com.github.bartcowski.gymkeeper.domain.user.*;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogCommand;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogEntry;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogName;
import com.github.bartcowski.gymkeeper.domain.workout.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class OnLaunchTestDataGenerator implements ApplicationRunner {

    private final UserService userService;

    private final WeightLogService weightLogService;

    private final WorkoutService workoutService;

    @Override
    public void run(ApplicationArguments args) {
        generateUsers();
        generateWeightLogs();
        generateWeightLogEntries();
        generateWorkouts();
        generateExercisesWithSets();
    }

    private void generateUsers() {
        CreateUserCommand createUserCommand1 = new CreateUserCommand(
                new Username("kowalski"),
                UserGender.MALE,
                new UserAge(23),
                new UserWeight(90.0),
                new UserHeight(185)
        );
        CreateUserCommand createUserCommand2 = new CreateUserCommand(
                new Username("nowacka"),
                UserGender.FEMALE,
                new UserAge(39),
                new UserWeight(55.1),
                new UserHeight(167)
        );
        userService.addUser(createUserCommand1);
        userService.addUser(createUserCommand2);
    }

    private void generateWeightLogs() {
        CreateWeightLogCommand createWeightLogCommand1 = new CreateWeightLogCommand(
                new UserId(0L),
                new WeightLogName("kowalski WeightLog"),
                LocalDate.of(2023, 1, 1)
        );
        CreateWeightLogCommand createWeightLogCommand2 = new CreateWeightLogCommand(
                new UserId(1L),
                new WeightLogName("nowacka WeightLog"),
                LocalDate.of(2022, 12, 15)
        );
        weightLogService.addWeightLog(createWeightLogCommand1);
        weightLogService.addWeightLog(createWeightLogCommand2);
    }

    private void generateWeightLogEntries() {
        WeightLogId weightLogId1 = new WeightLogId(0L);
        List<WeightLogEntry> entries1 = List.of(
                new WeightLogEntry(new UserWeight(86.4), LocalDate.of(2023, 1, 2)),
                new WeightLogEntry(new UserWeight(86.7), LocalDate.of(2023, 1, 4)),
                new WeightLogEntry(new UserWeight(86.6), LocalDate.of(2023, 1, 5)),
                new WeightLogEntry(new UserWeight(86.6), LocalDate.of(2023, 1, 6)),
                new WeightLogEntry(new UserWeight(86.4), LocalDate.of(2023, 1, 7)),
                new WeightLogEntry(new UserWeight(86.2), LocalDate.of(2023, 1, 9)),
                new WeightLogEntry(new UserWeight(86.1), LocalDate.of(2023, 1, 10)),
                new WeightLogEntry(new UserWeight(86.0), LocalDate.of(2023, 1, 11))
        );

        entries1.forEach(entry -> weightLogService.addWeightLogEntry(entry, weightLogId1));

        WeightLogId weightLogId2 = new WeightLogId(1L);
        List<WeightLogEntry> entries2 = List.of(
                new WeightLogEntry(new UserWeight(61.1), LocalDate.of(2022, 12, 26)),
                new WeightLogEntry(new UserWeight(61.1), LocalDate.of(2022, 12, 27)),
                new WeightLogEntry(new UserWeight(60.8), LocalDate.of(2022, 12, 28)),
                new WeightLogEntry(new UserWeight(60.9), LocalDate.of(2022, 12, 29)),
                new WeightLogEntry(new UserWeight(60.5), LocalDate.of(2022, 12, 30)),
                new WeightLogEntry(new UserWeight(61.5), LocalDate.of(2023, 1, 1)),
                new WeightLogEntry(new UserWeight(60.7), LocalDate.of(2023, 1, 2)),
                new WeightLogEntry(new UserWeight(60.2), LocalDate.of(2023, 1, 3))
        );

        entries2.forEach(entry -> weightLogService.addWeightLogEntry(entry, weightLogId2));
    }

    private void generateWorkouts() {
        CreateWorkoutCommand createWorkoutCommand1 = new CreateWorkoutCommand(
                new UserId(0),
                LocalDate.of(2023, 1, 1),
                false,
                "first workout after 2 weeks break"
        );
        CreateWorkoutCommand createWorkoutCommand2 = new CreateWorkoutCommand(
                new UserId(0),
                LocalDate.of(2023, 1, 4),
                false,
                ""
        );
        CreateWorkoutCommand createWorkoutCommand3 = new CreateWorkoutCommand(
                new UserId(1),
                LocalDate.of(2022, 12, 28),
                true,
                "last deload workout, I've already felt quite fresh"
        );

        List.of(createWorkoutCommand1, createWorkoutCommand2, createWorkoutCommand3).forEach(workoutService::addWorkout);
    }

    private void generateExercisesWithSets() {
        WorkoutId workoutId1 = new WorkoutId(0);
        CreateExerciseCommand createExerciseCommand1 = new CreateExerciseCommand(
                List.of(
                        new ExerciseSet(1, 10, 100),
                        new ExerciseSet(2, 10, 100)
                ), 1, ExerciseType.SQUAT, ""
        );
        CreateExerciseCommand createExerciseCommand2 = new CreateExerciseCommand(
                List.of(
                        new ExerciseSet(1, 15, 12.5),
                        new ExerciseSet(2, 14, 12.5),
                        new ExerciseSet(3, 12, 11)
                ), 2, ExerciseType.DUMBBELL_CURLS, "had to go down with weight on the last set"
        );
        List.of(createExerciseCommand1, createExerciseCommand2).forEach(e -> workoutService.addExercise(e, workoutId1));

        WorkoutId workoutId2 = new WorkoutId(1);
        CreateExerciseCommand createExerciseCommand3 = new CreateExerciseCommand(
                List.of(
                        new ExerciseSet(1, 5, 170),
                        new ExerciseSet(2, 5, 160),
                        new ExerciseSet(3, 5, 150),
                        new ExerciseSet(4, 5, 150)
                ), 1, ExerciseType.DEADLIFT, "definitely at least 3 reps in reserve on the last set"
        );
        List.of(createExerciseCommand3).forEach(e -> workoutService.addExercise(e, workoutId2));

        WorkoutId workoutId3 = new WorkoutId(2);
        CreateExerciseCommand createExerciseCommand4 = new CreateExerciseCommand(
                List.of(
                        new ExerciseSet(1, 12, 60),
                        new ExerciseSet(2, 10, 60)
                ), 1, ExerciseType.LAT_PULL_DOWN, ""
        );
        CreateExerciseCommand createExerciseCommand5 = new CreateExerciseCommand(
                List.of(
                        new ExerciseSet(1, 15, 7.5),
                        new ExerciseSet(2, 10, 8)
                ), 2, ExerciseType.CABLE_LATERAL_RAISE, ""
        );
        CreateExerciseCommand createExerciseCommand6 = new CreateExerciseCommand(
                List.of(
                        new ExerciseSet(1, 15, 25),
                        new ExerciseSet(2, 14, 25),
                        new ExerciseSet(3, 12, 25)
                ), 3, ExerciseType.TRICEPS_ROPE_PUSHDOWN, "slight pain in my left elbow"
        );
        List.of(createExerciseCommand4, createExerciseCommand5, createExerciseCommand6).forEach(e -> workoutService.addExercise(e, workoutId3));
    }
}
