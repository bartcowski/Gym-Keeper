package com.github.bartcowski.gymkeeper.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@Getter
public class User {

    private final UserId id;

    private final Username username;

    private final UserGender gender;

    private UserAge age;

    private UserWeight weight;

    private UserHeight height;

    public void updateWeight(UserWeight weight) {
        this.weight = weight;
    }

    public double calculateBMI() {
        double BMI = weight.value() / (height.inMetres() * height.inMetres());
        return new BigDecimal(BMI).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public double calculateFFMI(BodyFatPercentage bodyFatPercentage) {
        double totalBodyFat = weight.value() * (bodyFatPercentage.value() / 100.0);
        double leanWeight = weight.value() - totalBodyFat;
        double FFMI = leanWeight / (height.inMetres() * height.inMetres());
        double normalizedFFMI = FFMI + 6.1 * (1.8 - height.inMetres());
        return new BigDecimal(normalizedFFMI).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
