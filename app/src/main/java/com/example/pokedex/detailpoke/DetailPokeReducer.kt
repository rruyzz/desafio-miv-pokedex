package com.example.pokedex.detailpoke



object DetailPokeReducer {
    fun reduce(currentState: DetailPokemonState, result: DetailPokemonResult) : DetailPokemonState {
        return when (result) {
            DetailPokemonResult.Loanding -> currentState.copy(stateTypeDetail = StateTypeDetail.Loanding)
            DetailPokemonResult.SessionExpired -> currentState.copy(stateTypeDetail = StateTypeDetail.SessionExpired)
            is DetailPokemonResult.SucessDetailPokemon -> currentState.copy(
                stateTypeDetail = StateTypeDetail.SucessDetailPokemon,
                successDetailPoke = result.successDetailPoke
            )
            is DetailPokemonResult.ErrorDetailPokemon -> currentState.copy(
                stateTypeDetail = StateTypeDetail.ErrorDetailPokemon,
                messageDetail = result.message
            )
            is DetailPokemonResult.SuccessSpeciePokemon -> currentState.copy(
                stateTypeDetail = StateTypeDetail.SuccessSpeciePokemon,
                successSpeciePoke = result.successSpeciePokemon
            )
            is DetailPokemonResult.ErrorSpeciePokemon -> currentState.copy(
                stateTypeDetail = StateTypeDetail.ErrorSpeciePokemon,
                messageDetail = result.message
            )
        }
    }
}