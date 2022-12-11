package com.example.mountain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity (tableName = "mountain_table")
data class Mountain(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "mountainName")
    val mountainName: String = "",

    @ColumnInfo(name = "mountainImage")
    val mountainImage: String = "",

    @ColumnInfo(name = "location")
    val location: String = "",

    @ColumnInfo(name = "height")
    val height: Int = 0,

    @ColumnInfo(name = "description")
    val description: String = "",
): Parcelable
