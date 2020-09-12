package com.example.myapplication.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NetworkCAll {

    @GET("nzin01312019187360.json")
     fun getAllProfilesData() : Call<ResponseBody>

    companion object{
        operator fun invoke() : NetworkCAll {
            return Retrofit.Builder()
                .baseUrl("https://cricket.yahoo.net/sifeeds/cricket/live/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkCAll::class.java)
        }
    }
}