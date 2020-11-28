package com.luis.simplegymworkout.service

import android.content.Context
import com.luis.simplegymworkout.repository.MuscleDatabase

object MuscleServiceImp {

    fun getInstance(context: Context) : IMuscleService{
        //return TestMuscleServiceImp()
        return MuscleDatabase.getDatabase(context).getRepositoryService()
    }
}