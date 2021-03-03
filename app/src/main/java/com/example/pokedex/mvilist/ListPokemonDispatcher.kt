package com.example.pokedex.mvilist

import androidx.lifecycle.LiveDataScope
import com.example.pokedex.network.Repository

class ListPokemonDispatcher(private val repository: Repository) : ListPokemonMVIDispatcher() {
    override suspend fun LiveDataScope<ListPokemonResults>.handleAction(action: ListPokemonActions) {
        when (action){
            is ListPokemonActions.ListPokemonRequestAction -> {
                emit(ListPokemonResults.Loanding)
                val result = repository.getListPokemonService()
            }
        }
    }
}