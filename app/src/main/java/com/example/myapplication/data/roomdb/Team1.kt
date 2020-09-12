package com.example.myapplication.data.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Team1(
    @PrimaryKey
    val id: String,

    val PlaynerName : String,
    val position : Int,

    val isKeepner : Boolean = false,
    var isCaption: Boolean = false
)