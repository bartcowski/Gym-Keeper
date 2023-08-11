package com.github.bartcowski.gymkeeper.domain.user;

import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Value
public class User {

    UserId id;

    Username username;

    UserGender gender;

    UserAge age;

    UserWeight weight;

    UserHeight height;

    public User withUpdatedWeight(UserWeight weight) {
        return new User(id, username, gender, age, weight, height);
    }

    public double calculateBMI() {
        double BMI = weight.value() / (height.valueInMetres() * height.valueInMetres());
        return new BigDecimal(BMI).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public double calculateFFMI(BodyFatPercentage bodyFatPercentage) {
        double totalBodyFat = weight.value() * (bodyFatPercentage.value() / 100);
        double leanWeight = weight.value() - totalBodyFat;
        double FFMI = leanWeight / (height.valueInMetres() * height.valueInMetres());
        double normalizedFFMI = FFMI + 6.1 * (1.8 - height.valueInMetres());
        return new BigDecimal(normalizedFFMI).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
