package com.example.pokedex.detailpoke

import androidx.lifecycle.LiveDataScope
import com.example.pokedex.network.Repository
import com.example.pokedex.network.RetrofitRequest

class DetailPokeDispatcher(private val repository: Repository) : DetailPokemonMVIDispatcher() {
    override suspend fun LiveDataScope<DetailPokemonResult>.handleAction(action: DetailPokemonAction) {
        when(action){
            is DetailPokemonAction.DetailPokemonResquestAction -> {
                emit(DetailPokemonResult.Loanding)
                val result = RetrofitRequest.doRetrofitRequest("getPokemonService"){
                    repository.getPokemonService(action.id)
                }
                when {
                    result.hasError -> emit(DetailPokemonResult.ErrorDetailPokemon(result.message!!))
                    else -> emit((DetailPokemonResult.SucessDetailPokemon(result.response!!)))
                }
            }
            is DetailPokemonAction.SpeciePokemonRequestAction -> {
                emit(DetailPokemonResult.Loanding)
                val result = RetrofitRequest.doRetrofitRequest("getPokemonSpecieService"){
                    repository.getPokemonSpecieService(action.id)
                }
                when {
                    result.hasError -> emit(DetailPokemonResult.ErrorSpeciePokemon(result.message!!))
                    else -> emit((DetailPokemonResult.SuccessSpeciePokemon(result.response!!)))
                }
            }
        }
    }
}