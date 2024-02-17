package com.github.bartcowski.gymkeeper;

import com.github.bartcowski.gymkeeper.app.user.UserService;
import com.github.bartcowski.gymkeeper.app.weightlog.WeightLogService;
import com.github.bartcowski.gymkeeper.domain.user.*;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogCommand;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogEntryCommand;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogName;
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

    @Override
    public void run(ApplicationArguments args) {
        generateUsers();
        generateWeightLogs();
        generateWeightLogEntries();
    }

    private void generateWeightLogEntries() {
        WeightLogId weightLogId1 = new WeightLogId(0L);
        List<CreateWeightLogEntryCommand> entries1 = List.of(
                new CreateWeightLogEntryCommand(new UserWeight(86.4), LocalDate.of(2023, 1, 2)),
                new CreateWeightLogEntryCommand(new UserWeight(86.7), LocalDate.of(2023, 1, 4)),
                new CreateWeightLogEntryCommand(new UserWeight(86.6), LocalDate.of(2023, 1, 5)),
                new CreateWeightLogEntryCommand(new UserWeight(86.6), LocalDate.of(2023, 1, 6)),
                new CreateWeightLogEntryCommand(new UserWeight(86.4), LocalDate.of(2023, 1, 7)),
                new CreateWeightLogEntryCommand(new UserWeight(86.2), LocalDate.of(2023, 1, 9)),
                new CreateWeightLogEntryCommand(new UserWeight(86.1), LocalDate.of(2023, 1, 10)),
                new CreateWeightLogEntryCommand(new UserWeight(86.0), LocalDate.of(2023, 1, 11))
        );

        entries1.forEach(entry -> weightLogService.addWeightLogEntry(entry, weightLogId1));

        WeightLogId weightLogId2 = new WeightLogId(1L);
        List<CreateWeightLogEntryCommand> entries2 = List.of(
                new CreateWeightLogEntryCommand(new UserWeight(61.1), LocalDate.of(2022, 12, 26)),
                new CreateWeightLogEntryCommand(new UserWeight(61.1), LocalDate.of(2022, 12, 27)),
                new CreateWeightLogEntryCommand(new UserWeight(60.8), LocalDate.of(2022, 12, 28)),
                new CreateWeightLogEntryCommand(new UserWeight(60.9), LocalDate.of(2022, 12, 29)),
                new CreateWeightLogEntryCommand(new UserWeight(60.5), LocalDate.of(2022, 12, 30)),
                new CreateWeightLogEntryCommand(new UserWeight(61.5), LocalDate.of(2023, 1, 1)),
                new CreateWeightLogEntryCommand(new UserWeight(60.7), LocalDate.of(2023, 1, 2)),
                new CreateWeightLogEntryCommand(new UserWeight(60.2), LocalDate.of(2023, 1, 3))
        );

        entries2.forEach(entry -> weightLogService.addWeightLogEntry(entry, weightLogId2));
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

}
