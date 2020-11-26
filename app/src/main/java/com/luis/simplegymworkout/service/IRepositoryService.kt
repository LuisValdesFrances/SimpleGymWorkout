package com.luis.simplegymworkout.service

import androidx.room.Dao
import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.model.Group
import com.luis.simplegymworkout.model.Series
@Dao
interface IRepositoryService {

    fun getGroups() : ArrayList<Group>

    fun getExercises(groupName : String) : ArrayList<Exercise>

    fun getRepetitions(groupName : String, exerciseName : String) : ArrayList<Series>

    fun saveGroup(group : Group)

    fun deleteGroup(name : String)

    fun saveExercise(exercise: Exercise)

    fun deleteExercise(groupName: String, exerciseName : String)

}