package com.github.bartcowski.gymkeeper.app.user;

import com.github.bartcowski.gymkeeper.domain.user.UpdateUserDataCommand;
import com.github.bartcowski.gymkeeper.domain.user.UserAge;
import com.github.bartcowski.gymkeeper.domain.user.UserHeight;
import com.github.bartcowski.gymkeeper.domain.user.UserWeight;

public class UpdateUserDataCommandDTO {

    public int age;

    public double weight;

    public int height;

    public UpdateUserDataCommandDTO(int age, double weight, int height) {
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public UpdateUserDataCommand toDomain() {
        return new UpdateUserDataCommand(
                new UserAge(age),
                new UserWeight(weight),
                new UserHeight(height)
        );
    }
}
