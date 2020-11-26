package com.luis.simplegymworkout.repository

import android.util.Log
import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.model.Group
import com.luis.simplegymworkout.model.Series

object TestRepository {

    var groups = ArrayList<Group>()

    init {
        this.groups = ArrayList()
        createData(groups)
    }

    private fun createData(groups: ArrayList<Group>){
        groups.add(Group("Pecho",  ArrayList<Exercise>()))
        groups.add(Group("Piernas",  ArrayList<Exercise>()))
        groups.add(Group("Hombros",  ArrayList<Exercise>()))
        groups.add(Group("Espalda",  ArrayList<Exercise>()))
        groups.add(Group("Biceps",  ArrayList<Exercise>()))
        groups.add(Group("Triceps",  ArrayList<Exercise>()))

        for(group in groups){
            Log.d("Debug","Create group: ${group.name}")
            for(i in 1..6){
                val exercise = Exercise(group.name, "Exercise $i", ArrayList<Series>())
                Log.d("Debug","Create exercise: ${exercise.name}")
                for(j in 1..6){
                    val repetition = Series(exercise.name, 10 * j, 5.0 * j)
                    Log.d("Debug","Create series: rep:  ${repetition.repetition} weight ${repetition.weight}")
                    exercise.series.add(repetition)
                }
                group.exercises.add(exercise)
            }
        }
    }
}