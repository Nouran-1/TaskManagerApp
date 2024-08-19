package com.example.taskmanagerapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy. REPLACE )
    suspend fun addData (task: Task) : Long
    @Insert
    suspend fun addDataList (task: List<Task>)
   @Delete
   suspend fun deleteData (task: Task)
   @Query
   ("SELECT * FROM user_Task")
   suspend fun getData (): List<Task>





}