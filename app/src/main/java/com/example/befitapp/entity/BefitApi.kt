package com.example.befitapp.entity

import com.example.befitapp.service.BeFitApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BefitApi {
    fun http(): BeFitApiService {
        val retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
                "http://10.0.2.2:8080/"
            ).build()
        return retrofit.create(BeFitApiService::class.java)
    }
}