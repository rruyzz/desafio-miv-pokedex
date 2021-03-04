package com.example.pokedex.mvilist

object ListPokemonReducer {

    fun reduce(currentState: ListPokemonState, result: ListPokemonResults) : ListPokemonState{
        return when(result){
            ListPokemonResults.Loanding -> currentState.copy(stateType = StateType.Loanding)
            ListPokemonResults.SessionExpired -> currentState.copy(stateType = StateType.SessionExpired)
            is ListPokemonResults.SuccessListPokemon -> currentState.copy(
                stateType = StateType.SuccessList,
                successList = result.successListPokemon
            )
            is ListPokemonResults.ErrorListPokemon -> currentState.copy(
                stateType = StateType.ErrorList,
                message = result.message
            )
        }
    }
}