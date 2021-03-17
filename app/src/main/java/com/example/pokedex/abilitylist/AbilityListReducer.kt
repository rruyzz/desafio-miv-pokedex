package com.example.pokedex.abilitylist

object AbilityListReducer {
    fun reduce(currentState: AbilityState, results: AbilityResults): AbilityState {
        return when (results) {
            AbilityResults.Loanding -> currentState.copy(stateTypeAbility = StateTypeAbility.Loanding)
            is AbilityResults.SuccessListAbility -> currentState.copy(
                stateTypeAbility = StateTypeAbility.SuccessListAbility,
                successListAbility = results.successListAbility
            )
            is AbilityResults.ErrorListAbility -> currentState.copy(
                stateTypeAbility = StateTypeAbility.ErrorListAbility,
                message = results.message
            )

        }
    }
}