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

    public UserId getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public UserGender getGender() {
        return gender;
    }

    public UserAge getAge() {
        return age;
    }

    public UserWeight getWeight() {
        return weight;
    }

    public UserHeight getHeight() {
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
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && gender == user.gender && Objects.equals(age, user.age) && Objects.equals(weight, user.weight) && Objects.equals(height, user.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, gender, age, weight, height);
    }
}
