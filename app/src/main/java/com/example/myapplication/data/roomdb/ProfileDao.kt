package com.example.myapplication.data.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProfile(proEntity: ProfileEntity)

    @Update
    fun updateProfile(proEntity: ProfileEntity)

    @Delete
    fun deleteProfile(proEntity: ProfileEntity)

    @Query("SELECT * FROM ProfileEntity")
    fun getAllProfiles(): List<ProfileEntity>
}