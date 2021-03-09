package com.example.pokedex.di

import com.example.pokedex.detailpoke.DetailPokeDispatcher
import com.example.pokedex.detailpoke.DetailPokemonViewModel
import com.example.pokedex.mvilist.ListPokemonDispatcher
import com.example.pokedex.mvilist.ListPokemonViewModel
import com.example.pokedex.network.Repository
import com.example.pokedex.network.Service
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val module = module{
    factory { Repository(provideRetrofit()) }

    factory  { ListPokemonDispatcher(get())}
    viewModel { ListPokemonViewModel(get()) }

    factory { DetailPokeDispatcher(get()) }
    viewModel { DetailPokemonViewModel(get()) }
}


fun provideRetrofit(): Service {
    val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttp = OkHttpClient.Builder().addInterceptor(logger)
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
        .build()

    return retrofit.create(Service::class.java)

}

