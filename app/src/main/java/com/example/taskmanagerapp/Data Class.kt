package com.example.taskmanagerapp

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName ="user_task")
 data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var isCompleted: Boolean,
    var duration: Long,
    var remainingTime: Long
)
