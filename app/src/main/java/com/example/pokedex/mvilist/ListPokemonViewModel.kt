package com.example.pokedex.mvilist

import androidx.lifecycle.MutableLiveData
import com.example.mvi.core.currentState
import com.example.pokedex.model.PokemonResult

open class ListPokemonViewModel(dispatcher: ListPokemonDispatcher): ListPokemonMVIViewModel(dispatcher) {
    override fun reduce(result: ListPokemonResults) : ListPokemonState {
        return ListPokemonReducer.reduce(currentState, result)
    }




}