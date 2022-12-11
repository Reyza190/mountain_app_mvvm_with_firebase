package com.example.mountain.viewmodel

sealed class UiState<out T>{
    data class Succes<out T>(val result: T): UiState<T>()
    data class Failure(val exception: String?): UiState<Nothing>()
    object Loading: UiState<Nothing>()
}
