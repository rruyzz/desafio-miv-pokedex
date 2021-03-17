package com.example.pokedex.abilitylist

import com.example.mvi.core.MVIDispatcher
import com.example.mvi.core.MVIFragment
import com.example.mvi.core.MVIViewModel
import com.example.mvi_annotations.MVIActions
import com.example.mvi_annotations.MVIResults
import com.example.mvi_annotations.MVIState
import com.example.pokedex.model.PokemonResponse

@MVIState
data class AbilityState(
    val stateTypeAbility : StateTypeAbility? = null,
    val message: String = "",
    val successListAbility: PokemonResponse? = null)

@MVIActions
sealed class AbilityAction {
    data class ListAbilityRequestAction(val limit: Int, val offset: Int) : AbilityAction()
}

@MVIResults
sealed class AbilityResults{
    object Loanding: AbilityResults()
    data class SuccessListAbility(val successListAbility: PokemonResponse) : AbilityResults()
    data class ErrorListAbility(val message: String) : AbilityResults()
}

sealed class StateTypeAbility{
    object Loanding: StateTypeAbility()

    object SuccessListAbility : StateTypeAbility()
    object ErrorListAbility : StateTypeAbility()
}

typealias AbilityMVIViewModel = MVIViewModel<AbilityAction, AbilityResults, AbilityState>
typealias AbilityMVIDispatcher = MVIDispatcher<AbilityAction, AbilityResults>
typealias AbilityMVIFragment = MVIFragment<AbilityState>
