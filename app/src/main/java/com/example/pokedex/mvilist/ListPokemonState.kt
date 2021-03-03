package com.example.pokedex.mvilist

import com.example.mvi.core.MVIDispatcher
import com.example.mvi.core.MVIFragment
import com.example.mvi.core.MVIViewModel
import com.example.mvi_annotations.MVIActions
import com.example.mvi_annotations.MVIResults
import com.example.mvi_annotations.MVIState
import com.example.pokedex.model.PokemonResponse


@MVIState
data class ListPokemonState (
    val stateType: StateType? = null,
    val message: String = "",
    val successList : List<PokemonResponse>? = null
)

@MVIActions
sealed class ListPokemonActions {
    class ListPokemonRequestAction() : ListPokemonActions()
}

@MVIResults
sealed class ListPokemonResults{
    object Loanding : ListPokemonResults()
    object SessionExpired : ListPokemonResults()

    data class SuccessListPokemon(val successListPokemon: List<PokemonResponse>) : ListPokemonResults()
    data class ErrorListPokemon(val message: String) : ListPokemonResults()
}

sealed class StateType{
    object Loanding : StateType()
    object SessionExpired : StateType()
    object SuccessCep : StateType()
    object ErrorCep : StateType()
}

typealias ListPokemonMVIViewModel = MVIViewModel<ListPokemonActions, ListPokemonResults, ListPokemonState>
typealias ListPokemonMVIDispatcher = MVIDispatcher<ListPokemonActions, ListPokemonResults>
typealias ListPokemonMVIFragment = MVIFragment<ListPokemonState>