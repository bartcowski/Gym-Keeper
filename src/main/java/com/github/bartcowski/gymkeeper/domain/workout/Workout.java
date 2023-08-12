package com.github.bartcowski.gymkeeper.domain.workout;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import lombok.Value;

import java.util.List;

@Value
public class Workout {

    UserId userId; //workout creator, loose coupling between User and Workout

    List<Exercise> exercises;

    boolean isDeload;

    String comment;

}
