package com.example.pokedex.mvilist

import com.example.mvi.core.MVIDispatcher
import com.example.mvi.core.MVIFragment
import com.example.mvi.core.MVIViewModel
import com.example.mvi_annotations.MVIActions
import com.example.mvi_annotations.MVIResults
import com.example.mvi_annotations.MVIState
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonResponse
import com.example.pokedex.model.PokemonResult


@MVIState
data class ListPokemonState (
    val stateType: StateType? = null,
    val message: String = "",
    val successList : PokemonResponse? = null,
    val successSearch : Pokemon? = null
)

@MVIActions
sealed class ListPokemonActions {
    data class ListPokemonRequestAction(val limit: Int, val offset: Int) : ListPokemonActions()
    data class SearchPokemonRequestAction(val pokemonName: String): ListPokemonActions()
}

@MVIResults
sealed class ListPokemonResults{
    object Loanding : ListPokemonResults()
    object SessionExpired : ListPokemonResults()

    data class SuccessListPokemon(val successListPokemon: PokemonResponse) : ListPokemonResults()  //Passa o Result
    data class ErrorListPokemon(val message: String) : ListPokemonResults()

    data class SuccessSearchPokemon(val successSearch: Pokemon) : ListPokemonResults()
    data class ErrorSearchPokemon(val message: String): ListPokemonResults()
}

sealed class StateType{
    object Loanding : StateType()
    object SessionExpired : StateType()
    object SuccessList : StateType()
    object ErrorList : StateType()
    object SuccessSearch : StateType()
    object ErrorSearch : StateType()
}

typealias ListPokemonMVIViewModel = MVIViewModel<ListPokemonActions, ListPokemonResults, ListPokemonState>
typealias ListPokemonMVIDispatcher = MVIDispatcher<ListPokemonActions, ListPokemonResults>
typealias ListPokemonMVIFragment = MVIFragment<ListPokemonState>