package com.luis.simplegymworkout.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "series",
    foreignKeys = [ForeignKey(entity = Exercise::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("exercise_id"),
        onDelete = ForeignKey.CASCADE)]
)
data class Series (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id : Int = 0,
        @ColumnInfo(name = "exercise_id")
        var exerciseInt: Int,
        @ColumnInfo(name = "repetition")
        var repetition: Int,
        @ColumnInfo(name = "weight")
        var weight: Double)