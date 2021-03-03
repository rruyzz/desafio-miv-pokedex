package com.example.pokedex.mvilist

import com.example.mvi.core.currentState
import com.example.pokedex.mvilist.ListPokemonReducer.reduce

open class ListPokemonViewModel(dispatcher: ListPokemonDispatcher): ListPokemonMVIViewModel(dispatcher) {

    override fun reduce(result: ListPokemonResults) : ListPokemonState =
        ListPokemonReducer.reduce(currentState, result)

}