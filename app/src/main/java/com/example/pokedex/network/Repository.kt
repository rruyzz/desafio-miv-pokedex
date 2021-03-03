package com.example.pokedex.network


import com.example.pokedex.model.PokemonResponse
import com.example.pokedex.model.PokemonResult
import retrofit2.Response

class Repository(private val service: Service) {

    suspend fun getListPokemonService() : Response<PokemonResponse>{
        return service.getListPokemonService(1,0)
    }
}