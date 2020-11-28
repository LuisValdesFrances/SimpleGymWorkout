package com.luis.simplegymworkout.repository

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.model.Muscle
import com.luis.simplegymworkout.model.Series
import com.luis.simplegymworkout.service.IMuscleService

@Database(entities = [Muscle::class, Exercise::class, Series::class], version = 1)
abstract class MuscleDatabase : RoomDatabase() {

    abstract fun getRepositoryService() : IMuscleService

    companion object {
        private lateinit var context: Context
        private val database: MuscleDatabase by lazy(LazyThreadSafetyMode.SYNCHRONIZED){

            Log.d("Debug","################################")
            Log.d("Debug","Create local database as Singleton")
            Log.d("Debug","################################")

            Room.databaseBuilder(context,
                MuscleDatabase::class.java,
                "simple_gym_workout_db")
                .allowMainThreadQueries()
                .build()
        }

        fun getDatabase(context: Context): MuscleDatabase {
            Companion.context = context.applicationContext
            return database
        }
    }
}