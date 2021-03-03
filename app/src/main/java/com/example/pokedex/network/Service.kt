package com.example.pokedex.network

import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("pokemon")
    suspend fun getListPokemonService(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonService(@Path("id") id:String) : Pokemon


}

val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
val okHttp = OkHttpClient.Builder().addInterceptor(logger)
val retrofit = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttp.build())
    .build()

val service: Service = retrofit.create(Service::class.java)