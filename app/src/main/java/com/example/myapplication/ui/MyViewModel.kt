package com.example.myapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repositories.ProfilesRepo
import com.example.myapplication.data.roomdb.ProfileEntity

class MyViewModel(val profilesRepo: ProfilesRepo) : ViewModel() {

    var mProfilesReponse =  MutableLiveData<List<ProfileEntity>>()
    private set

    init {
        mProfilesReponse = profilesRepo.savedProfiles
    }

    fun getAllProfiles(){
        profilesRepo.getData()
    }
}
