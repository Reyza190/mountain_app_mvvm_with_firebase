package com.example.mountain.repository

import android.net.Uri
import com.example.mountain.model.Mountain
import com.example.mountain.viewmodel.Resource
import com.example.mountain.viewmodel.UiState

interface MountainRepository {
    fun getMountains(result: (UiState<List<Mountain>>) -> Unit)
    fun addMountains(mountain: Mountain, result: (UiState<String>) -> Unit)
    fun getMountainById(id: String, result: (UiState<Mountain>) -> Unit)
    suspend fun uploadImage(fileUri: Uri, onResult: (UiState<Uri>) -> Unit)
}