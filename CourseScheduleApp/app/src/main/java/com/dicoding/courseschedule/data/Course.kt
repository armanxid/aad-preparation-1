package com.dicoding.courseschedule.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

//TODO 1 : Define a local database table using the schema in app/schema/course.json
@Entity(tableName = "course")
data class Course(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NotNull
    val id: Int = 0,

    @ColumnInfo(name = "courseName")
    @NotNull
    val courseName: String,

    @ColumnInfo(name = "day")
    @NotNull
    val day: Int,

    @ColumnInfo(name = "startTime")
    @NotNull
    val startTime: String,

    @ColumnInfo(name = "endTime")
    @NotNull
    val endTime: String,

    @ColumnInfo(name = "lecturer")
    @NotNull
    val lecturer: String,

    @ColumnInfo(name = "note")
    @NotNull
    val note: String
)
