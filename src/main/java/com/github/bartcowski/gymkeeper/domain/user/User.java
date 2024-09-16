package com.github.bartcowski.gymkeeper.domain.user;

import com.github.bartcowski.gymkeeper.util.DoubleUtil;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "app_user")
public class User {

    @EmbeddedId
    private UserId id;

    @Embedded
    private Username username;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Embedded
    private UserAge age;

    @Embedded
    private UserWeight weight;

    @Embedded
    private UserHeight height;

    public User(UserId id, Username username, UserGender gender, UserAge age, UserWeight weight, UserHeight height) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    protected User() {
        //persistence
    }

    public UserId id() {
        return id;
    }

    public Username username() {
        return username;
    }

    public UserGender gender() {
        return gender;
    }

    public UserAge age() {
        return age;
    }

    public UserWeight weight() {
        return weight;
    }

    public UserHeight height() {
        return height;
    }

    public void updateAge(UserAge age) {
        this.age = age;
    }

    public void updateWeight(UserWeight weight) {
        this.weight = weight;
    }

    public void updateHeight(UserHeight height) {
        this.height = height;
    }

    public double calculateBMI() {
        double BMI = weight.weight() / (height.inMetres() * height.inMetres());
        return DoubleUtil.roundDoubleToTwoDecimalPlaces(BMI);
    }

    public double calculateFFMI(BodyFatPercentage bodyFatPercentage) {
        double totalBodyFat = weight.weight() * (bodyFatPercentage.value() / 100.0);
        double leanWeight = weight.weight() - totalBodyFat;
        double FFMI = leanWeight / (height.inMetres() * height.inMetres());
        double normalizedFFMI = FFMI + 6.1 * (1.8 - height.inMetres());
        return DoubleUtil.roundDoubleToTwoDecimalPlaces(normalizedFFMI);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
