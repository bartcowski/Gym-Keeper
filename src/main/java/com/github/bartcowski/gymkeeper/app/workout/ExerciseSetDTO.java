package com.github.bartcowski.gymkeeper.app.workout;

import com.github.bartcowski.gymkeeper.domain.workout.ExerciseSet;
import com.github.bartcowski.gymkeeper.util.DoubleUtil;

public class ExerciseSetDTO {

    public int index;

    public int reps;

    public double weight;

    public ExerciseSetDTO(int index, int reps, double weight) {
        this.index = index;
        this.reps = reps;
        this.weight = weight;
    }

    public static ExerciseSetDTO fromDomain(ExerciseSet exerciseSet) {
        return new ExerciseSetDTO(
                exerciseSet.index(),
                exerciseSet.reps(),
                DoubleUtil.roundDoubleToTwoDecimalPlaces(exerciseSet.weight())
        );
    }

    public ExerciseSet toDomain() {
        return new ExerciseSet(index, reps, weight);
    }
}
