package com.example.myapplication.data.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfileEntity(
    @PrimaryKey
    val id: String,

    val profileName : String,
    val profileAge : Int,
    val profileGender : String,
    val profileLocationCity : String,
    val profileLocationState : String,
    val profileImageUrl : String,

    val isDeclined : Boolean = false,
    var isAccepted: Boolean = false
)