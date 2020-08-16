package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repositories.ProfilesRepo

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(
    private val profilesRepo: ProfilesRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyViewModel(profilesRepo) as T
    }
}