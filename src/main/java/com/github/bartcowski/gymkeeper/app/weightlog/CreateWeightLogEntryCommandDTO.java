package com.github.bartcowski.gymkeeper.app.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserWeight;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogEntryCommand;

import java.time.LocalDate;

public class CreateWeightLogEntryCommandDTO {

    double weight;

    LocalDate date;

    String comment;

    public CreateWeightLogEntryCommand toDomain() {
        return new CreateWeightLogEntryCommand(
                new UserWeight(weight),
                date,
                comment
        );
    }

}
