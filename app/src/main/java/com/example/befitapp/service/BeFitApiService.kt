package com.example.befitapp.service

import com.example.befitapp.entity.Catalogo
import com.example.befitapp.entity.Login
import com.example.befitapp.entity.Usuario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface BeFitApiService {

    @GET("/treinos/catalogo/{personId}")
    fun getTreinos(@Path("personId") personId: String): Call<List<Catalogo>>

    @POST("/treinos/favoritar/{personId}/{treinoId}")
    fun favoritar(@Path("personId") personId: String, @Path("treinoId") treinoId: Int): Call<String>

    @DELETE("/treinos/desfavoritar/{personId}/{treinoId}")
    fun desfavoritar(@Path("personId") personId: String, @Path("treinoId") treinoId: Int): Call<String>

    @POST("/usuarios")
    fun adicionarUsuario(@Body usuario: Usuario): Call<Usuario>

    @PATCH("usuarios/login/{email}/{senha}")
    fun loginUsuario(@Path("email") email: String, @Path("senha") senha: String): Call<Login>
}
