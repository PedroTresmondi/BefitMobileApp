package com.example.befitapp.service

import com.example.befitapp.entity.*
import retrofit2.Call
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
    fun loginUsuario(@Path("email") email: String, @Path("senha") senha: String): Call<LoginResponse>

    @GET("/treinos/{id}")
    fun getTreino(@Path("id") id: Int): Call<List<Exercicio>>

    @GET("/treinos/favoritos/{personId}")
    fun getTreinoFavoritos(@Path("personId") personId: String): Call<List<Catalogo>>

    @GET("/dietas/favoritos/{personId}")
    fun getDietaFavoritos(@Path("personId") personId: String): Call<List<Catalogo>>


    @GET("/dietas/catalogo/{personId}")
    fun getDietas(@Path("personId") personId: String): Call<List<Dieta>>

    @GET("/dietas/{dietaId}")
    fun getDietaUnique(@Path("dietaId") dietaId: Int): Call<Dieta>

    @POST("/dietas/favoritar/{personId}/{dietaId}")
    fun favoritarDieta(@Path("personId") personId: String, @Path("dietaId") dietaId: Int): Call<String>

    @DELETE("/dietas/desfavoritar/{personId}/{dietaId}")
    fun desfavoritarDieta(@Path("personId") personId: String, @Path("dietaId") dietaId: Int): Call<String>

    @GET("/dietas/{dietaId}")
    fun getIngrediente(@Path("dietaId") dietaId: Int): Call<List<Ingrediente>>


}
