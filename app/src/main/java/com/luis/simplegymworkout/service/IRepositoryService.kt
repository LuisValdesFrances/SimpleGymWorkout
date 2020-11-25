package com.luis.simplegymworkout.service

import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.model.Group
import com.luis.simplegymworkout.model.Repetition

interface IRepositoryService {

    fun getGroups() : ArrayList<Group>

    fun getExercises(groupName : String) : ArrayList<Exercise>

    fun getRepetitions(groupName : String, exerciseName : String) : ArrayList<Repetition>

    fun saveGroup(group : Group)

    fun deleteGroup(name : String)

    fun saveExercise(exercise: Exercise)

    fun deleteExercise(groupName: String, exerciseName : String)

}