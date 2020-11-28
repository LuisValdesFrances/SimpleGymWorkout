package com.luis.simplegymworkout.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle")
data class Muscle(
        @PrimaryKey
        @ColumnInfo(name = "name")
        var name: String)