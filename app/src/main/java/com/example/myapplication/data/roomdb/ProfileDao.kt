package com.example.myapplication.data.roomdb

import androidx.room.*

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam1(proEntity: Team1)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam2(proEntity: Team2)

    @Update
    fun updateProfile(proEntity: Team1)

    @Delete
    fun deleteProfile(proEntity: Team1)

    @Query("SELECT * FROM Team1 ORDER BY position ASC")
    fun getAllPlayersFromTeam1(): List<Team1>

    @Query("SELECT * FROM Team2 ORDER BY position ASC")
    fun getAllPlayersFromTeam2(): List<Team2>
}