package com.github.bartcowski.gymkeeper.domain.workout

import com.github.bartcowski.gymkeeper.domain.user.UserId
import spock.lang.Specification

import java.time.LocalDate

class WorkoutTest extends Specification {

    def "should update workout"() {
        given:
        def workout = createWorkoutWithExercises([])
        def newDate = workout.getDate().plusDays(1)
        def newIsDeload = !workout.isDeload()
        def newComment = "updated comment"
        def command = new UpdateWorkoutCommand(newDate, newIsDeload, newComment)

        when:
        workout.updateWorkout(command)

        then:
        workout.getDate() == newDate
        workout.isDeload() == newIsDeload
        workout.getComment() == newComment
    }

    def "should add new exercise"() {
        given:
        def workout = createWorkoutWithExercises([
                new Exercise(new ExerciseId(123), 1, ExerciseType.SQUAT, [])
        ])

        when:
        workout.addExercise(
                new Exercise(new ExerciseId(456), 2, ExerciseType.BARBELL_BENCH_PRESS, []))

        then:
        workout.exercises.size() == 2
    }

    def "should not allow to add exercise if any existing has the same type or index"() {
        given:
        def workout = createWorkoutWithExercises([
                new Exercise(new ExerciseId(123), 1, ExerciseType.SQUAT, [])
        ])

        when:
        workout.addExercise(newInvalidExercise)

        then:
        thrown(IllegalStateException.class)
        workout.exercises.size() == 1

        where:
        newInvalidExercise << [
                new Exercise(new ExerciseId(456), 2, ExerciseType.SQUAT, []),
                new Exercise(new ExerciseId(456), 1, ExerciseType.BARBELL_BENCH_PRESS, [])
        ]
    }

    def "should not allow to add exercise if its sets have duplicated indexes"() {
        given:
        def workout = createWorkoutWithExercises([])

        when:
        workout.addExercise(new Exercise(new ExerciseId(456), 2, ExerciseType.SQUAT, [
                new ExerciseSet(1, 5, 90), new ExerciseSet(1, 7, 100),
        ]))

        then:
        thrown(IllegalStateException.class)
        workout.exercises.size() == 0
    }

    def "should update exercise and its sets"() {
        given:
        def workout = createWorkoutWithExercises([
                new Exercise(new ExerciseId(123), 1, ExerciseType.SQUAT, [
                        new ExerciseSet(1, 5, 100)
                ])
        ])

        when:
        workout.updateExercise(
                new Exercise(new ExerciseId(123), 1, ExerciseType.BARBELL_BENCH_PRESS, [
                        new ExerciseSet(1, 10, 50)
                ]))

        then:
        workout.exercises.size() == 1
        workout.exercises[0].exerciseType == ExerciseType.BARBELL_BENCH_PRESS
        workout.exercises[0].sets[0].reps == 10
        workout.exercises[0].sets[0].weight == 50
    }

    def "should not allow to update exercise if any existing has the same type or index"() {
        given:
        def workout = createWorkoutWithExercises([
                new Exercise(new ExerciseId(123), 1, ExerciseType.SQUAT, []),
                new Exercise(new ExerciseId(456), 2, ExerciseType.DEADLIFT, [])
        ])

        when:
        workout.updateExercise(updatedInvalidExercise)

        then:
        thrown(IllegalStateException.class)
        workout.exercises.size() == 2
        workout.exercises[0].exerciseType == ExerciseType.SQUAT
        workout.exercises[1].exerciseType == ExerciseType.DEADLIFT

        where:
        updatedInvalidExercise << [
                new Exercise(new ExerciseId(123), 1, ExerciseType.DEADLIFT, []),
                new Exercise(new ExerciseId(123), 2, ExerciseType.SQUAT, [])
        ]
    }

    def "should not allow to update exercise if its sets have duplicated indexes"() {
        given:
        def workout = createWorkoutWithExercises([
                new Exercise(new ExerciseId(123), 1, ExerciseType.SQUAT, [
                        new ExerciseSet(1, 5, 100)
                ])
        ])

        when:
        workout.updateExercise(
                new Exercise(new ExerciseId(123), 1, ExerciseType.SQUAT, [
                        new ExerciseSet(1, 10, 50),
                        new ExerciseSet(1, 12, 40)
                ]))

        then:
        thrown(IllegalStateException.class)
        workout.exercises.size() == 1
        workout.exercises[0].sets.size() == 1
        workout.exercises[0].sets[0].reps == 5
        workout.exercises[0].sets[0].weight == 100
    }

    def "should remove exercise of given ID and decrement indexes of all following exercises"() {
        given:
        def workout = createWorkoutWithExercises([
                new Exercise(new ExerciseId(12), 1, ExerciseType.SQUAT, []),
                new Exercise(new ExerciseId(34), 2, ExerciseType.DEADLIFT, []),
                new Exercise(new ExerciseId(56), 3, ExerciseType.PULL_UP, []),
                new Exercise(new ExerciseId(78), 4, ExerciseType.DUMBBELL_CHEST_FLY, [])
        ])
        def exerciseIdToDelete = new ExerciseId(34)

        when:
        workout.deleteExercise(exerciseIdToDelete)

        then:
        workout.exercises.size() == 3
        workout.exercises[0].exerciseType == ExerciseType.SQUAT
        workout.exercises[0].index == 1
        workout.exercises[1].exerciseType == ExerciseType.PULL_UP
        workout.exercises[1].index == 2
        workout.exercises[2].exerciseType == ExerciseType.DUMBBELL_CHEST_FLY
        workout.exercises[2].index == 3
    }

    private static Workout createWorkoutWithExercises(List<Exercise> exercises) {
        return new Workout(
                new WorkoutId(1),
                new UserId(10),
                exercises,
                LocalDate.of(2023, 8, 16),
                false,
                "comment"
        )
    }
}
