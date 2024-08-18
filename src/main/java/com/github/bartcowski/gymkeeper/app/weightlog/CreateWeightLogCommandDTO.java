package com.github.bartcowski.gymkeeper.app.weightlog;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.weightlog.CreateWeightLogCommand;
import com.github.bartcowski.gymkeeper.domain.weightlog.WeightLogName;

import java.time.LocalDate;

public class CreateWeightLogCommandDTO {

    long userId;

    String name;

    LocalDate startDate;

    public CreateWeightLogCommand toDomain() {
        return new CreateWeightLogCommand(
                new UserId(userId),
                new WeightLogName(name),
                startDate
        );
    }

}
