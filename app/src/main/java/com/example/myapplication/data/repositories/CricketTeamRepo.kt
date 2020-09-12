package com.example.myapplication.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.MvvmApplication
import com.example.myapplication.data.network.NetworkCAll
import com.example.myapplication.data.roomdb.AppDatabase
import com.example.myapplication.data.roomdb.Team1
import com.example.myapplication.data.roomdb.Team2
import com.example.myapplication.util.Coroutins
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CricketTeamRepo(val networkCAll: NetworkCAll)  {

    private val Team1 = 1
    private val Team2 = 2

//    var apiResponse = MutableLiveData<String>()

    var savedPlayersTeam1 = MutableLiveData<List<Team1>>()
    var savedPlayerTeam2 = MutableLiveData<List<Team2>>()

    fun getData() {

        if (isNetworkAvailable(MvvmApplication.context)){
            loadTeamFromNW()
        }else{
            Toast.makeText(MvvmApplication.context,"No Internet Connection",Toast.LENGTH_LONG).show()
            getTeam1FromRoom()
            getTeam2FromRoom()
        }
    }

    fun loadTeamFromNW(){
        networkCAll.getAllProfilesData().enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MvvmApplication.context,"Failure Occured",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    var resp = response.body()?.string()

                    var jsonObject = JSONObject(resp)
                    val jsonObject1 = jsonObject.getJSONObject("Teams").getJSONObject("4").getJSONObject("Players")
                    val jsonObject2 = jsonObject.getJSONObject("Teams").getJSONObject("5").getJSONObject("Players")
                    generateObject(jsonObject1,Team1)
                    generateObject(jsonObject2,Team2)
                }
            }

        })
    }

    fun generateObject(jsonObject: JSONObject, teamNo: Int) {

        val keys: Iterator<String> = jsonObject.keys()

        while (keys.hasNext()) {
            var key = keys.next()
            var innerJsonObject = jsonObject.getJSONObject(key)

            var isKeeper = false
            if (innerJsonObject.has("Iskeeper")) {
                isKeeper = true
            }

            var isCaptain = false
            if (innerJsonObject.has("Iscaptain")) {
                isCaptain = true
            }

            if (teamNo == Team1) {
                var team = Team1(
                    key, innerJsonObject.getString("Name_Full"),
                    innerJsonObject.getInt("Position"), isKeeper, isCaptain
                )
                saveInDBTeam1(team)
            }else{

                var team = Team2(
                    key, innerJsonObject.getString("Name_Full"),
                    innerJsonObject.getInt("Position"), isKeeper, isCaptain
                )
                saveInDBTeam2(team)
            }
        }

    }

    fun saveInDBTeam1(team: Team1) {

            Coroutins.io {
                AppDatabase(MvvmApplication.context).getProfileDao().insertTeam1(team)
                withContext(Dispatchers.Main) {
                    getTeam1FromRoom()
                }
            }
        }

    fun saveInDBTeam2(team: Team2) {

        Coroutins.io {
            AppDatabase(MvvmApplication.context).getProfileDao().insertTeam2(team)
            withContext(Dispatchers.Main) {
                    getTeam2FromRoom()
            }
        }
    }

    fun getTeam1FromRoom() {
        Coroutins.io {

            val getSavedProfiles = AppDatabase(MvvmApplication.context).getProfileDao().getAllPlayersFromTeam1()

            withContext(Dispatchers.Main){
                savedPlayersTeam1.value = getSavedProfiles
            }
        }
    }

    fun getTeam2FromRoom() {
        Coroutins.io {

            val getSavedProfiles = AppDatabase(MvvmApplication.context).getProfileDao().getAllPlayersFromTeam2()

            withContext(Dispatchers.Main){
                savedPlayerTeam2.value = getSavedProfiles
            }
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // For 29 api or above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                        ?: return false
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
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
