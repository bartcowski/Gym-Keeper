package com.github.bartcowski.gymkeeper;

import com.github.bartcowski.gymkeeper.app.user.UserDTO;
import com.github.bartcowski.gymkeeper.app.user.UserService;
import com.github.bartcowski.gymkeeper.app.weightlog.WeightLogDTO;
import com.github.bartcowski.gymkeeper.app.weightlog.WeightLogService;
import com.github.bartcowski.gymkeeper.app.workout.WorkoutDTO;
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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
@Profile("prod")
public class OnLaunchTestDataGenerator implements ApplicationRunner {

    private final UserService userService;

    private final WeightLogService weightLogService;

    private final WorkoutService workoutService;

    @Override
    public void run(ApplicationArguments args) {
        List<UserDTO> twoGeneratedUsers = generateUsers();
        List<WeightLogDTO> twoGeneratedWeightLogs = generateWeightLogs(twoGeneratedUsers);
        generateWeightLogEntries(twoGeneratedWeightLogs);
        List<WorkoutDTO> threeGeneratedWorkouts = generateWorkouts(twoGeneratedUsers);
        generateExercisesWithSets(threeGeneratedWorkouts);
    }

    private List<UserDTO> generateUsers() {
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
        UserDTO user1 = userService.addUser(createUserCommand1);
        UserDTO user2 = userService.addUser(createUserCommand2);
        return List.of(user1, user2);
    }

    private List<WeightLogDTO> generateWeightLogs(List<UserDTO> twoGeneratedUsers) {
        CreateWeightLogCommand createWeightLogCommand1 = new CreateWeightLogCommand(
                new UserId(twoGeneratedUsers.get(0).id),
                new WeightLogName("kowalski WeightLog"),
                LocalDate.of(2023, 1, 1)
        );
        CreateWeightLogCommand createWeightLogCommand2 = new CreateWeightLogCommand(
                new UserId(twoGeneratedUsers.get(1).id),
                new WeightLogName("nowacka WeightLog"),
                LocalDate.of(2022, 12, 15)
        );
        WeightLogDTO weightLog1 = weightLogService.addWeightLog(createWeightLogCommand1);
        WeightLogDTO weightLog2 = weightLogService.addWeightLog(createWeightLogCommand2);
        return List.of(weightLog1, weightLog2);
    }

    private void generateWeightLogEntries(List<WeightLogDTO> twoGeneratedWeightLogs) {
        WeightLogId weightLogId1 = new WeightLogId(twoGeneratedWeightLogs.get(0).id);
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

        WeightLogId weightLogId2 = new WeightLogId(twoGeneratedWeightLogs.get(1).id);
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

    private List<WorkoutDTO> generateWorkouts(List<UserDTO> twoGeneratedUsers) {
        CreateWorkoutCommand createWorkoutCommand1 = new CreateWorkoutCommand(
                new UserId(twoGeneratedUsers.get(0).id),
                LocalDate.of(2023, 1, 1),
                false,
                "first workout after 2 weeks break"
        );
        CreateWorkoutCommand createWorkoutCommand2 = new CreateWorkoutCommand(
                new UserId(twoGeneratedUsers.get(0).id),
                LocalDate.of(2023, 1, 4),
                false,
                ""
        );
        CreateWorkoutCommand createWorkoutCommand3 = new CreateWorkoutCommand(
                new UserId(twoGeneratedUsers.get(1).id),
                LocalDate.of(2022, 12, 28),
                true,
                "last deload workout, I've already felt quite fresh"
        );

        WorkoutDTO workout1 = workoutService.addWorkout(createWorkoutCommand1);
        WorkoutDTO workout2 = workoutService.addWorkout(createWorkoutCommand2);
        WorkoutDTO workout3 = workoutService.addWorkout(createWorkoutCommand3);
        return List.of(workout1, workout2, workout3);
    }

    private void generateExercisesWithSets(List<WorkoutDTO> threeGeneratedWorkouts) {
        WorkoutId workoutId1 = new WorkoutId(threeGeneratedWorkouts.get(0).id);
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

        WorkoutId workoutId2 = new WorkoutId(threeGeneratedWorkouts.get(1).id);
        CreateExerciseCommand createExerciseCommand3 = new CreateExerciseCommand(
                List.of(
                        new ExerciseSet(1, 5, 170),
                        new ExerciseSet(2, 5, 160),
                        new ExerciseSet(3, 5, 150),
                        new ExerciseSet(4, 5, 150)
                ), 1, ExerciseType.DEADLIFT, "definitely at least 3 reps in reserve on the last set"
        );
        List.of(createExerciseCommand3).forEach(e -> workoutService.addExercise(e, workoutId2));

        WorkoutId workoutId3 = new WorkoutId(threeGeneratedWorkouts.get(2).id);
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
