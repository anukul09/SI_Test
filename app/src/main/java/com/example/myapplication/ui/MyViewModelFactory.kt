package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repositories.CricketTeamRepo

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(
    private val cricketTeamRepo: CricketTeamRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyViewModel(cricketTeamRepo) as T
    }
}