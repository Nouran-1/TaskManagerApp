package com.example.taskmanagerapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [Task::class], version = 2, exportSchema = false)
abstract class ListDatabase : RoomDatabase() {
        abstract fun listDao(): TaskDao

        companion object {
            @Volatile
            private var roomInstance: ListDatabase? = null
            fun getDatabase(context: Context): ListDatabase {
                if (roomInstance == null) {
                    synchronized(this) {
                        roomInstance = Room.databaseBuilder(
                            context = context, klass = ListDatabase::class.java, name = "list_database"
                        ).fallbackToDestructiveMigration().build()

                    }
                }
                return roomInstance!!
            }
        }
    }