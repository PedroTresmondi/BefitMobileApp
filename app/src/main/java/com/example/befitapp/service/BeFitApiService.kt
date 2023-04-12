package com.example.befitapp.service

import com.example.befitapp.entity.Catalogo
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface BeFitApiService {

    @GET("/treinos/catalogo/{personId}")
    fun getTreinos(@Path("personId") personId: String): Call<List<Catalogo>>

    @POST("/treinos/favoritar/{personId}/{treinoId}")
    fun favoritar(@Path("personId") personId: String, @Path("treinoId") treinoId: Int): Call<String>

    @DELETE("/treinos/desfavoritar/{personId}/{treinoId}")
    fun desfavoritar(@Path("personId") personId: String, @Path("treinoId") treinoId: Int): Call<String>

}