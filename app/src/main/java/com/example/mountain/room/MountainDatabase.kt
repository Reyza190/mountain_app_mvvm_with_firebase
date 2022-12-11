package com.example.mountain.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mountain.model.Mountain
import com.example.mountain.model.`interface`.MountainDao

@Database(entities = arrayOf(Mountain::class), version = 1)
abstract class MountainDatabase: RoomDatabase(){
    abstract fun dao(): MountainDao

    companion object {
        @Volatile
        private var INSTANCE: MountainDatabase? = null

        fun getInstance(context: Context): MountainDatabase{

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MountainDatabase::class.java,
                    "mountain_db"
                ).build()

                INSTANCE = instance

                instance

            }
        }
    }
}