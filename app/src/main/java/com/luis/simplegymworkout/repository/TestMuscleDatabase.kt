package com.luis.simplegymworkout.repository

import android.util.Log
import com.luis.simplegymworkout.model.Muscle

object TestMuscleDatabase {

    var groups = ArrayList<Muscle>()

    init {
        Log.d("Debug","################################")
        Log.d("Debug","Create test database as Singleton")
        Log.d("Debug","################################")
        this.groups = ArrayList()
        this.createDatabase(groups)
    }

    private fun createDatabase(muscles: ArrayList<Muscle>){
        muscles.add(Muscle("Pecho"))
        muscles.add(Muscle("Piernas"))
        muscles.add(Muscle("Hombros"))
        muscles.add(Muscle("Espalda"))
        muscles.add(Muscle("Biceps"))
        muscles.add(Muscle("Triceps"))

        /*
        var exerciseId = 0;
        var seriesId = 0;
        for(group in groups){
            //Log.d("Debug","Create group: ${group.name}")
            for(i in 1..6){
                val exercise = Exercise(exerciseId++, group.name, "Exercise $i", ArrayList<Series>())
                //Log.d("Debug","Create exercise: ${exercise.name}")
                for(j in 1..6){
                    val series = Series(seriesId++, exercise.id, 10 * j, 5.0 * j)
                    //Log.d("Debug","Create series: rep:  ${repetition.repetition} weight ${repetition.weight}")
                    exercise.series.add(series)
                }
                group.exercises.add(exercise)
            }
        }
        */
    }
}