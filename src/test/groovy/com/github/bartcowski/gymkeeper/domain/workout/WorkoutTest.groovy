package com.github.bartcowski.gymkeeper.domain.workout

import com.github.bartcowski.gymkeeper.domain.user.UserId
import spock.lang.Specification

import java.time.LocalDate

class WorkoutTest extends Specification {

    def "should add exercise of new type"() {
        given:
        def workout = createWorkoutWithExercises([
                new Exercise(new ExerciseId(1), ExerciseType.SQUAT, [
                        new ExerciseSet(1, 5, 122.5)
                ])
        ])

        when:
        workout.updateExercises([
                new Exercise(new ExerciseId(2), ExerciseType.BARBELL_BENCH_PRESS, [
                        new ExerciseSet(1, 15, 10.0)
                ])
        ])

        then:
        workout.exercises.size() == 2
    }

    def "should replace exercise if given already existing type"() {
        given:
        def workout = createWorkoutWithExercises([
                new Exercise(new ExerciseId(1), ExerciseType.SQUAT, [
                        new ExerciseSet(1, 5, 122.5)
                ])
        ])

        when:
        workout.updateExercises([
                new Exercise(new ExerciseId(1), ExerciseType.SQUAT, [
                        new ExerciseSet(1, 8, 100.0),
                        new ExerciseSet(2, 8, 100.0)
                ])
        ])

        then:
        workout.exercises.size() == 1
        workout.exercises.get(0).sets().size() == 2
    }

    def "should add exercises of new types and replace exercises of already existing types"() {
        given:
        def existingExercises = createValidSampleExercises()
        def workout = createWorkoutWithExercises(existingExercises)

        when:
        workout.updateExercises([
                new Exercise(new ExerciseId(1), ExerciseType.SQUAT, [
                        new ExerciseSet(1, 5, 122.5),
                        new ExerciseSet(2, 6, 120.0)
                ]),
                new Exercise(new ExerciseId(2), ExerciseType.CABLE_LATERAL_RAISE, [
                        new ExerciseSet(1, 15, 10.0),
                        new ExerciseSet(2, 15, 10.0)
                ])
        ])

        then:
        workout.exercises.size() == 3
        squatExerciseHasTwoSetsInsteadOfThree(workout)
    }

    def "should throw exception when #reason"() {
        given:
        def existingExercises = []
        def workout = createWorkoutWithExercises(existingExercises)

        when:
        workout.updateExercises(exercises)

        then:
        thrown(IllegalStateException.class)
        workout.exercises == existingExercises

        where:
        reason                                                  | exercises
        "given exercises contain duplicate types"               | createExercisesWithDuplicateTypes()
        "at least one exercise has contain duplicate set index" | createExercisesWithDuplicateSetIndex()
    }

    private ArrayList<Exercise> createValidSampleExercises() {
        return [
                new Exercise(new ExerciseId(1), ExerciseType.SQUAT, [
                        new ExerciseSet(1, 5, 122.5),
                        new ExerciseSet(2, 6, 120.0),
                        new ExerciseSet(3, 6, 120.0)
                ]),
                new Exercise(new ExerciseId(2), ExerciseType.BARBELL_BENCH_PRESS, [
                        new ExerciseSet(1, 8, 90.0),
                        new ExerciseSet(2, 10, 87.5),
                        new ExerciseSet(3, 12, 82.5)
                ])
        ]
    }

    private ArrayList<Exercise> createExercisesWithDuplicateTypes() {
        return [
                new Exercise(new ExerciseId(1), ExerciseType.SQUAT, [
                        new ExerciseSet(1, 5, 122.5),
                        new ExerciseSet(2, 6, 120.0)
                ]),
                new Exercise(new ExerciseId(2), ExerciseType.SQUAT, [
                        new ExerciseSet(1, 8, 90.0),
                        new ExerciseSet(2, 10, 87.5)
                ])
        ]
    }

    private ArrayList<Exercise> createExercisesWithDuplicateSetIndex() {
        return [
                new Exercise(new ExerciseId(1), ExerciseType.SQUAT, [
                        new ExerciseSet(1, 5, 122.5),
                        new ExerciseSet(2, 6, 120.0),
                        new ExerciseSet(2, 6, 120.0)
                ])
        ]
    }

    private Workout createWorkoutWithExercises(List<Exercise> exercises) {
        return new Workout(
                new WorkoutId(1),
                new UserId(10),
                exercises,
                LocalDate.of(2023, 8, 16),
                true,
                "comment"
        )
    }

    private boolean squatExerciseHasTwoSetsInsteadOfThree(Workout workout) {
        workout.exercises.stream().filter(w -> w.exerciseType() == ExerciseType.SQUAT).findFirst().get().sets().size() == 2
    }

}
