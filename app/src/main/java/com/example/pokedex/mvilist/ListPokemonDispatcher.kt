package com.example.pokedex.mvilist

import androidx.lifecycle.LiveDataScope
import com.example.pokedex.network.Repository
import com.example.pokedex.network.RetrofitRequest

class ListPokemonDispatcher(private val repository: Repository) : ListPokemonMVIDispatcher() {
    override suspend fun LiveDataScope<ListPokemonResults>.handleAction(action: ListPokemonActions) {
        when (action) {
            is ListPokemonActions.ListPokemonRequestAction -> {
                emit(ListPokemonResults.Loanding)
                val result = RetrofitRequest.doRetrofitRequest("getListPokemonService") {
                    repository.getListPokemonService(action.limit, action.offset)
                }
                when{
                    result.hasError ->emit(ListPokemonResults.ErrorListPokemon(result.message!!))
                    else -> emit((ListPokemonResults.SuccessListPokemon(result.response!!)))
                }
            }
        }
    }
}