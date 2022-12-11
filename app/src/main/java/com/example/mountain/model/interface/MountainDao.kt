package com.example.mountain.model.`interface`

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mountain.model.Mountain
import java.util.concurrent.Flow

@Dao
interface MountainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mountain: Mountain)

    @Query("SELECT * FROM mountain_table")
    fun getAllMountains(): LiveData<List<Mountain>>

    @Delete
    suspend fun deleteMountain(mountain: Mountain)
}