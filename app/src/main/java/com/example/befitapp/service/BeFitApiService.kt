package com.example.befitapp.service

import com.example.befitapp.entity.PlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApiService {
    @GET("json")
    suspend fun getGyms(
        @Query("location") location: String,
        @Query("radius") radius: Int? = 2000,
        @Query("types") types: String? = "gym",
        @Query("key") key: String? = "AIzaSyAghQgS67kmlLDvmOBAlyf7UDEjEmH_0R8"
    ): PlaceResponse
}