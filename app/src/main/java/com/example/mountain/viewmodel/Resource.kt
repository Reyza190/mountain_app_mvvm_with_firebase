package com.example.mountain.viewmodel

import com.example.mountain.R

sealed class Resource<out R>{
    data class Succes<out R>(val result: R): Resource<R>()
    data class Failure(val exception: Exception): Resource<Nothing>()
    object Loading: Resource<Nothing>()

}