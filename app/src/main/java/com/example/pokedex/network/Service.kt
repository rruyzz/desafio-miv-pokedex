package com.example.pokedex.network

import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonResponse
import com.example.pokedex.model.PokemonSpecies
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("pokemon")
    suspend fun getListPokemonService(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): Response<PokemonResponse>

    @GET("ability")
    suspend fun getListPokemonAbility(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): Response<PokemonResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonService(@Path("id") id: String): Response<Pokemon>

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecieService(@Path("id") id: String): Response<PokemonSpecies>
}

val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
val okHttp = OkHttpClient.Builder().addInterceptor(logger)
val retrofit = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttp.build())
    .build()

val service: Service = retrofit.create(Service::class.java)
