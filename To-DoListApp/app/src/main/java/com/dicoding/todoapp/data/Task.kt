package com.dicoding.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

//TODO 1 : Define a local database table using the schema in app/schema/tasks.json
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NotNull
    val id: Int = 0,
    @ColumnInfo(name = "title")
    @NotNull
    val title: String,
    @ColumnInfo(name = "description")
    @NotNull
    val description: String,
    @ColumnInfo(name = "dueDate")
    @NotNull
    val dueDateMillis: Long,
    @ColumnInfo(name = "completed")
    @NotNull
    val isCompleted: Boolean = false
)
