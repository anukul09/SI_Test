package com.example.myapplication.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.MvvmApplication
import com.example.myapplication.data.network.NetworkCAll
import com.example.myapplication.data.network.profileMappingResponses.Json4Kotlin_Base
import com.example.myapplication.data.roomdb.AppDatabase
import com.example.myapplication.data.roomdb.ProfileEntity
import com.example.myapplication.util.Coroutins
import kotlinx.coroutines.*

class ProfilesRepo(val networkCAll: NetworkCAll)  {

    private var check = true

    var apiResponse = MutableLiveData<Json4Kotlin_Base>()

    var savedProfiles = MutableLiveData<List<ProfileEntity>>()

    fun getData() {

        if (isNetworkAvailable(MvvmApplication.context)){
            testData()
        }else{
            getDataFromRoom()
        }
    }

    fun testData()  {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = networkCAll.getAllProfilesData()
                withContext(Dispatchers.Main) {
                    apiResponse.value = result.body()
                    check=true

                    Log.d("Repo","Main Called")

                    saveInDB(apiResponse.value)

                }
            } catch (e: Exception) {
                //e.printStackTrace()
                withContext(Dispatchers.Main) {
                    testData()
                    check= false
                    Log.d("Anu1", e.message)
                }
            }
        }
    }

    fun saveInDB(ijs: Json4Kotlin_Base?) {
        for (i in 0 until ijs!!.results.size) {

            Log.d("Anu", "called")
            val profileList = ijs.results[i]
            val profile = ProfileEntity(
                profileList.email,
                profileList.name.first,
                profileList.dob.age,
                profileList.gender,
                profileList.location.city,
                profileList.location.state,
                profileList.picture.large,
                false,
                false
            )

            Coroutins.io {
                AppDatabase(MvvmApplication.context).getProfileDao().insertProfile(profile)
                withContext(Dispatchers.Main){
                    getDataFromRoom()
                }
            }
        }

    }

    fun getDataFromRoom() {
        Coroutins.io {
            var getSavedProfiles = AppDatabase(MvvmApplication.context).getProfileDao().getAllProfiles()

            withContext(Dispatchers.Main){
                savedProfiles.value = getSavedProfiles
            }
            Log.d("Anu",savedProfiles.value?.size.toString())
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // For 29 api or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->    true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->   true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->   true
                else ->     false
            }
        }
        // For below 29 api
        else {
            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }

}