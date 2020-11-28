package com.luis.simplegymworkout.service

import android.content.Context
import android.util.Log
import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.model.Muscle
import com.luis.simplegymworkout.model.Series
import com.luis.simplegymworkout.repository.MuscleDatabase

class MuscleServiceImp(private val context: Context) {

    private var muscleService : IMuscleService

    init{
        Log.i("Debug", "Init muscle service imp")
        this.muscleService  = MuscleDatabase.getDatabase(this.context).getRepositoryService()
    }

    fun isMuscleExist(muscleName : String) : Boolean{
        val muscles = this.muscleService.getMuscles()
        for(muscle in muscles){
            if(muscle.name.toUpperCase() == muscleName.toUpperCase()){
                return true
            }
        }
        return false
    }

    fun isExerciseExist(muscleName : String, exerciseId : Int, exerciseName : String) : Boolean{
        val exercises = this.muscleService.getExercises(muscleName)
        for(exercise in exercises){
            if(exercise.id !=  exerciseId && exercise.name.toUpperCase() == exerciseName.toUpperCase()){
                return true
            }
        }
        return false
    }

    fun isExerciseExist(muscleName : String, exerciseName : String) : Boolean{
        val exercises = this.muscleService.getExercises(muscleName)
        for(exercise in exercises){
            if(exercise.name.toUpperCase() == exerciseName.toUpperCase()){
                return true
            }
        }
        return false
    }

    fun getExerciseByName(muscleName : String, exerciseName : String) : Exercise? {
        val exercises = this.muscleService.getExercises(muscleName)
        for(exercise in exercises){
            if(exercise.name == exerciseName){
                return exercise
            }
        }
        return null
    }

    fun saveMuscle(muscle: Muscle){
        //TODO: Comporbar que le nombre no se repite
        this.muscleService.saveMuscle(muscle)
    }

    fun saveExercise(exercise: Exercise){
        //TODO: Comporbar que le nombre no se repite
        this.muscleService.saveExercise(exercise)
    }

    fun saveSeries(series: Series){
        this.muscleService.saveSeries(series)
    }

    fun updateExercise(exercise : Exercise){
        this.muscleService.updateExercise(exercise)
    }

    fun getMuscles() : List<Muscle>{
        return this.muscleService.getMuscles()
    }

    fun getExercises(muscleName : String) : List<Exercise>{
        return this.muscleService.getExercises(muscleName)
    }

    fun getExercise(exerciseId: Int) : Exercise{
        return this.muscleService.getExercise(exerciseId)
    }

    fun getSeries(exerciseId: Int) : List<Series>{
        return this.muscleService.getSeries(exerciseId)
    }

    fun deleteMuscle(muscleName: String){
        this.muscleService.deleteMuscle(muscleName)
    }

    fun deleteExercise(exerciseId: Int){
        this.muscleService.deleteExercise(exerciseId)
    }

    fun deleteSeriesFromExercise(exerciseId: Int){
        this.muscleService.deleteSeriesFromExercise(exerciseId)
    }
}