package com.example.pokedex.mvilist

import androidx.lifecycle.MutableLiveData
import com.example.mvi.core.currentState
import com.example.pokedex.model.PokemonResult


open class ListPokemonViewModel(dispatcher: ListPokemonDispatcher): ListPokemonMVIViewModel(dispatcher) {

//    val listPoke = MutableLiveData<Array<List<PokemonResult>>>()
    override fun reduce(result: ListPokemonResults) : ListPokemonState {
//        val results = StateType.SuccessList.
        return ListPokemonReducer.reduce(currentState, result)
    }




}