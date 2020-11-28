package com.luis.simplegymworkout.service

import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.model.Muscle
import com.luis.simplegymworkout.model.Series

class TestMuscleServiceImp : IMuscleService {
    override fun saveMuscle(muscle: Muscle) {
        TODO("Not yet implemented")
    }

    override fun saveExercise(exercise: Exercise) {
        TODO("Not yet implemented")
    }

    override fun saveSeries(series: Series) {
        TODO("Not yet implemented")
    }

    override fun updateExercise(exercise: Exercise) {
        TODO("Not yet implemented")
    }

    override fun getMuscles(): ArrayList<Muscle> {
        TODO("Not yet implemented")
    }

    override fun getMuscle(name: String): Muscle {
        TODO("Not yet implemented")
    }

    override fun getExercises(muscleGroupName: String): ArrayList<Exercise> {
        TODO("Not yet implemented")
    }

    override fun getExercise(id: Int): Exercise {
        TODO("Not yet implemented")
    }

    override fun getSeries(exerciseId: Int): ArrayList<Series> {
        TODO("Not yet implemented")
    }

    override fun deleteMuscle(name: String) {
        TODO("Not yet implemented")
    }

    override fun deleteExercise(id: Int) {
        TODO("Not yet implemented")
    }

    override fun deleteExerciseFromMuscle(muscleName: String) {
        TODO("Not yet implemented")
    }

    override fun deleteSeriesFromExercise(exerciseId: Int) {
        TODO("Not yet implemented")
    }
}