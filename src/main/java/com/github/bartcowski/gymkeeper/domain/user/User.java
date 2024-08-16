package com.github.bartcowski.gymkeeper.domain.user;

import com.github.bartcowski.gymkeeper.util.DoubleUtil;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
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
        return DoubleUtil.roundDoubleToTwoDecimalPlaces(BMI);
    }

    public double calculateFFMI(BodyFatPercentage bodyFatPercentage) {
        double totalBodyFat = weight.value() * (bodyFatPercentage.value() / 100.0);
        double leanWeight = weight.value() - totalBodyFat;
        double FFMI = leanWeight / (height.inMetres() * height.inMetres());
        double normalizedFFMI = FFMI + 6.1 * (1.8 - height.inMetres());
        return DoubleUtil.roundDoubleToTwoDecimalPlaces(normalizedFFMI);
    }
}
