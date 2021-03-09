package com.example.pokedex.detailpoke

import com.example.mvi.core.currentState

open class DetailPokemonViewModel(dispatcher: DetailPokeDispatcher) : DetailPokemonMVIViewModel(dispatcher){
    override fun reduce(result: DetailPokemonResult): DetailPokemonState {
        return DetailPokeReducer.reduce(currentState, result)
    }
}