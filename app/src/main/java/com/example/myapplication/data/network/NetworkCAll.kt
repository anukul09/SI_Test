package com.example.myapplication.data.network

import com.example.myapplication.data.network.profileMappingResponses.Json4Kotlin_Base

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NetworkCAll {

    @GET("?results=10")
    suspend fun getAllProfilesData() : Response<Json4Kotlin_Base>

    companion object{
        operator fun invoke() : NetworkCAll {
            return Retrofit.Builder()
                .baseUrl("https://randomuser.me/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkCAll::class.java)
        }
    }
}