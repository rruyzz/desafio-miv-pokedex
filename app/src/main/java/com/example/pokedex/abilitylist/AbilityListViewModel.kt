package com.example.pokedex.abilitylist

import com.example.mvi.core.currentState

open class AbilityListViewModel(dispatcher: AbilityListDispatcher) : AbilityMVIViewModel(dispatcher) {
    override fun reduce(result: AbilityResults): AbilityState {
        return AbilityListReducer.reduce(currentState, result)
    }
}