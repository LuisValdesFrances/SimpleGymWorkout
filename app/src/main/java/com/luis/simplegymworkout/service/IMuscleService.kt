package com.luis.simplegymworkout.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.model.Muscle
import com.luis.simplegymworkout.model.Series

@Dao
interface IMuscleService {

    @Insert
    fun saveMuscle(muscle : Muscle)

    @Insert
    fun saveExercise(exercise: Exercise)

    @Insert
    fun saveSeries(series: Series)

    @Update
    fun updateExercise(exercise: Exercise)

    @Query("SELECT * FROM muscle")
    fun getMuscles() : List<Muscle>

    @Query("SELECT * FROM muscle WHERE name =:name")
    fun getMuscle(name : String) : Muscle

    @Query("SELECT * FROM exercise WHERE muscle_name =:muscleName")
    fun getExercises(muscleName : String) : List<Exercise>

    @Query("SELECT * FROM exercise WHERE id =:id")
    fun getExercise(id : Int) : Exercise

    @Query("SELECT * FROM series WHERE exercise_id =:exerciseId")
    fun getSeries(exerciseId : Int) : List<Series>

    @Query("DELETE FROM muscle WHERE name =:name")
    fun deleteMuscle(name : String)

    @Query("DELETE FROM exercise WHERE id =:id")
    fun deleteExercise(id : Int)

    @Query("DELETE FROM exercise WHERE exercise.muscle_name =:muscleName")
    fun deleteExerciseFromMuscle(muscleName : String)

    @Query("DELETE FROM series WHERE exercise_id =:exerciseId")
    fun deleteSeriesFromExercise(exerciseId : Int)
}