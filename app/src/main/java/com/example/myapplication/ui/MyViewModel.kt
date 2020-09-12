package com.example.myapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repositories.CricketTeamRepo
import com.example.myapplication.data.roomdb.Team1
import com.example.myapplication.data.roomdb.Team2

class MyViewModel(val cricketTeamRepo: CricketTeamRepo) : ViewModel() {

    var mRespTeam1 =  MutableLiveData<List<Team1>>()
    private set

    var mRespTeam2 =  MutableLiveData<List<Team2>>()
    private set

    init {
        mRespTeam1 = cricketTeamRepo.savedPlayersTeam1
        mRespTeam2 = cricketTeamRepo.savedPlayerTeam2
    }

    fun getAllProfiles(){
        cricketTeamRepo.getData()
    }
//
//    override fun onCleared() {
//        super.onCleared()
//
//        Log.d("Anu", "oncleared called")
//
//    }
}
