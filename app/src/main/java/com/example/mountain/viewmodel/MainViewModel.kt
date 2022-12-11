package com.example.mountain.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mountain.DummyCategory
import com.example.mountain.DummyMountain

class MainViewModel: ViewModel() {
    fun getAllCategory() = DummyCategory.getAllCategory()
    fun getAllMountain() = DummyMountain.getAllMountain()
}