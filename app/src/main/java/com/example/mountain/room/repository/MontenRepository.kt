package com.example.mountain.room.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mountain.model.Mountain
import com.example.mountain.model.`interface`.MountainDao
import com.example.mountain.room.MountainDatabase
import com.example.mountain.viewmodel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MontenRepository(private val mountainDao: MountainDao){

    val allMountain: LiveData<List<Mountain>> = mountainDao.getAllMountains()

    suspend fun insert(mountain: Mountain){
        mountainDao.insert(mountain)
    }

}