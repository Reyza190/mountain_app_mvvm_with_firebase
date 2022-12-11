package com.example.mountain.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mountain.model.Mountain
import com.example.mountain.repository.MountainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MountainViewModel @Inject constructor(
    val repository: MountainRepository
): ViewModel() {

    private val _mountain = MutableLiveData<UiState<List<Mountain>>>()
    val mountain: LiveData<UiState<List<Mountain>>> = _mountain

    private val _addMountain = MutableLiveData<UiState<String>>()
    val addMountain: LiveData<UiState<String>> = _addMountain

    fun addMountains(mountain: Mountain){
        _addMountain.value = UiState.Loading
        repository.addMountains(mountain){
            _addMountain.value = it
        }
    }

    fun getMountain(){
        _mountain.value = UiState.Loading
        repository.getMountains {
            _mountain.value = it
        }
    }

    fun uploadImage(fileUri: Uri, onResult: (UiState<Uri>) -> Unit){
        onResult.invoke(UiState.Loading)
        viewModelScope.launch {
            repository.uploadImage(fileUri, onResult)
        }
    }

}