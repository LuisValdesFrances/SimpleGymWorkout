package com.luis.simplegymworkout.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "exercise",
    foreignKeys = [ForeignKey(entity = Muscle::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("muscle_name"),
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)]
)
data class Exercise(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id : Int = 0,
        @ColumnInfo(name = "muscle_name")
        var muscleName: String,
        @ColumnInfo(name = "name")
        var name: String)