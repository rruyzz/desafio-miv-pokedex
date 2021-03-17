package com.example.pokedex.abilitylist

import androidx.lifecycle.LiveDataScope
import com.example.pokedex.network.Repository
import com.example.pokedex.network.RetrofitRequest

class AbilityListDispatcher(private val repository: Repository) : AbilityMVIDispatcher() {
    override suspend fun LiveDataScope<AbilityResults>.handleAction(action: AbilityAction) {
        when (action) {
            is AbilityAction.ListAbilityRequestAction -> {
                emit(AbilityResults.Loanding)
                val result = RetrofitRequest.doRetrofitRequest("getListPokemonAbility") {
                    repository.getListPokemonAbility(action.limit, action.offset)
                }
                when {
                    result.hasError -> emit(AbilityResults.ErrorListAbility(result.message!!))
                    else -> emit(AbilityResults.SuccessListAbility(result.response!!))
                }
            }
        }
    }
}