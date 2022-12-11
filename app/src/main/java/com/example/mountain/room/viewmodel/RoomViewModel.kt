package com.example.mountain.room.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mountain.model.Mountain
import com.example.mountain.model.`interface`.MountainDao
import com.example.mountain.room.MountainDatabase
import com.example.mountain.room.repository.MontenRepository
import com.example.mountain.viewmodel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application){

    private val repository: MontenRepository

    val getAllMountains: LiveData<List<Mountain>>

    init {
        val dao = MountainDatabase.getInstance(application).dao()
        repository = MontenRepository(dao)
        getAllMountains = repository.allMountain
    }

    fun insertMountains(mountain: Mountain) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(mountain)
    }

}