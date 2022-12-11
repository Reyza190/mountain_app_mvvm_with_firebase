package com.example.mountain

import com.example.mountain.model.Category

object DummyCategory {

    fun getAllCategory() = listOf(Category(id = "1", "  All  "),Category(id = "3", title = "Jawa Timur"),
    Category(id = "2", title = "Jawa Tengah"), Category(id = "5",  title = "Jawa Barat"),
        Category(id = "6", title = "Nusa Tenggara barat"))

}
