package com.example.befitapp.entity

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("json")
    suspend fun getGyms(
        @Query("location") location: String,
        @Query("radius") radius: Int? = 2000,
        @Query("tyoes") types: String? = "gym",
        @Query("key") key: String? = "AIzaSyAghQgS67kmlLDvmOBAlyf7UDEjEmH_0R8"
    ): PlaceResponse
}