package com.example.pokedex.mvilist

object ListPokemonReducer {

    fun reduce(currentState: ListPokemonState, results: ListPokemonResults) : ListPokemonState{
        return when(results){
            ListPokemonResults.Loanding -> currentState.copy(stateType = StateType.Loanding)
            ListPokemonResults.SessionExpired -> currentState.copy(stateType = StateType.SessionExpired)
            is ListPokemonResults.SuccessListPokemon -> currentState.copy(
                stateType = StateType.SuccessCep,
                successList = results.successListPokemon
            )
            is ListPokemonResults.ErrorListPokemon -> currentState.copy(
                stateType = StateType.ErrorCep,
                message = results.message
            )
        }
    }
}