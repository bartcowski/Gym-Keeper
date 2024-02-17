package com.github.bartcowski.gymkeeper;

import com.github.bartcowski.gymkeeper.app.user.UserDTO;
import com.github.bartcowski.gymkeeper.app.user.UserService;
import com.github.bartcowski.gymkeeper.app.weightlog.WeightLogDTO;
import com.github.bartcowski.gymkeeper.app.weightlog.WeightLogService;
import com.github.bartcowski.gymkeeper.domain.user.*;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogCommand;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogEntryCommand;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogId;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WeightLogEntryAddedAndUserWeightUpdatedIntegrationTest {

    private final UserWeight initialUserWeight = new UserWeight(90.0);

    private final UserWeight updatedUserWeight = new UserWeight(95.5);

    @Autowired
    private WeightLogService weightLogService;

    @Autowired
    private UserService userService;

    @Test
    void shouldAddWeightLogAndItsEntryAndThenUpdateUserWeightByPublishingEnEvent() {
        //when: user and their new empty weight log saved
        UserId userId = addUser();
        WeightLogId weightLogId = addWeightLog(userId);
        WeightLogDTO savedWeightLog = weightLogService.findWeightLogById(weightLogId).orElse(null);

        //then: saved weight log is present and linked to a proper user
        assertNotNull(savedWeightLog);
        assertEquals(userId.id(), savedWeightLog.userId);

        //when: new entry is added to the weight log (event informing about weight change published as a result)
        CreateWeightLogEntryCommand entryCommand = new CreateWeightLogEntryCommand(updatedUserWeight, LocalDate.EPOCH);
        weightLogService.addWeightLogEntry(entryCommand, new WeightLogId(savedWeightLog.id));
        UserDTO user = userService.findUserById(userId).orElse(null);

        //then: user is still present and their weight is updated
        assertNotNull(user);
        assertEquals(updatedUserWeight.value(), user.weight);
    }

    private WeightLogId addWeightLog(UserId userId) {
        CreateWeightLogCommand command = new CreateWeightLogCommand(
                userId,
                new WeightLogName("weightlog"),
                LocalDate.MIN
        );
        return new WeightLogId(weightLogService.addWeightLog(command).id);
    }

    private UserId addUser() {
        CreateUserCommand createUserCommand = new CreateUserCommand(
                new Username("username"),
                UserGender.MALE,
                new UserAge(23),
                initialUserWeight,
                new UserHeight(185)
        );
        return new UserId(userService.addUser(createUserCommand).id);
    }

}
