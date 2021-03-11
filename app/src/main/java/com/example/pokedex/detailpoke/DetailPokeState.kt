package com.example.pokedex.detailpoke

import com.example.mvi.core.MVIDispatcher
import com.example.mvi.core.MVIFragment
import com.example.mvi.core.MVIViewModel
import com.example.mvi_annotations.MVIActions
import com.example.mvi_annotations.MVIResults
import com.example.mvi_annotations.MVIState
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonSpecies
import com.example.pokedex.mvilist.StateType

@MVIState
data class DetailPokemonState(
    val stateTypeDetail: StateTypeDetail? = null,
    val messageDetail: String = "",
    val successDetailPoke : Pokemon? = null,
    val successSpeciePoke: PokemonSpecies? = null
)

@MVIActions
sealed class DetailPokemonAction {
    data class DetailPokemonResquestAction(val id: String) : DetailPokemonAction()
    data class SpeciePokemonRequestAction(val id: String) : DetailPokemonAction()
}

@MVIResults
sealed class DetailPokemonResult(){
    object Loanding : DetailPokemonResult()
    object SessionExpired : DetailPokemonResult()

    data class SucessDetailPokemon(val successDetailPoke: Pokemon) : DetailPokemonResult()
    data class ErrorDetailPokemon(val message: String) : DetailPokemonResult()

    data class SuccessSpeciePokemon(val successSpeciePokemon: PokemonSpecies) :DetailPokemonResult()
    data class ErrorSpeciePokemon(val message: String) : DetailPokemonResult()
}

sealed class StateTypeDetail{
    object Loanding : StateTypeDetail()
    object SessionExpired : StateTypeDetail()
    object SucessDetailPokemon : StateTypeDetail()
    object ErrorDetailPokemon : StateTypeDetail()
    object SuccessSpeciePokemon : StateTypeDetail()
    object ErrorSpeciePokemon : StateTypeDetail()
}

typealias DetailPokemonMVIViewModel = MVIViewModel<DetailPokemonAction, DetailPokemonResult, DetailPokemonState>
typealias DetailPokemonMVIDispatcher = MVIDispatcher<DetailPokemonAction, DetailPokemonResult>
typealias DetailPokemonMVIFragment = MVIFragment<DetailPokemonState>



//@MVIState
//data class DetailPokemonState (
//    val stateType: StateType? = null,
//    val message: String = "",
//    val successDetailPokemon : Pokemon? = null
//)
//
//@MVIActions
//sealed class DetailPokemonActions {
//    data class DetailPokemonRequestAction(val id: Int) : DetailPokemonActions()
//}
//
//@MVIResults
//sealed class DetailPokemonResults{
//    object Loanding : DetailPokemonResults()
//    object SessionExpired : DetailPokemonResults()
//
//    data class SuccessDetailPokemon(val successDetailPokemon: Pokemon) : DetailPokemonResults()  //Passa o Result
//    data class ErrorDetailPokemon(val message: String) : DetailPokemonResults()
//}
//
//sealed class StateTypeDetail{
//    object Loanding : StateTypeDetail()
//    object SessionExpired : StateType()
//    object SuccessDetailPokemon : StateType()
//    object ErrorDetailPokemon : StateType()
//}
//
//typealias DetailPokemonMVIViewModel = MVIViewModel<DetailPokemonActions, DetailPokemonResults, DetailPokemonState>
//typealias DetailPokemonMVIDispatcher = MVIDispatcher<DetailPokemonActions, DetailPokemonResults>
//typealias DetailPokemonMVIFragment = MVIFragment<DetailPokemonState>