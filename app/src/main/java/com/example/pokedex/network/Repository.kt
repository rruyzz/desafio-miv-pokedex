package com.example.pokedex.network


import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonResponse
import com.example.pokedex.model.PokemonResult
import com.example.pokedex.model.PokemonSpecies
import retrofit2.Response

class Repository(private val service: Service) {

    suspend fun getListPokemonService( limit: Int, offset: Int) : Response<PokemonResponse> {
        return service.getListPokemonService(limit, offset)
    }

    suspend fun getPokemonService(id: String) : Response<Pokemon>{
        return service.getPokemonService(id)
    }

    suspend fun getPokemonSpecieService(id: String) : Response<PokemonSpecies>{
        return service.getPokemonSpecieService(id)
    }

    suspend fun getListPokemonAbility(limit: Int, offset: Int) : Response<PokemonResponse>{
        return service.getListPokemonAbility(limit, offset)
    }
}