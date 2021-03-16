package com.example.pokedex.detailpoke

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mvi.UiStateMachine
import com.example.pokedex.R
import com.example.pokedex.R.*
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonSpecies
import com.example.pokedex.mvilist.ListPokemonState
import com.example.pokedex.mvilist.StateType
import kotlinx.android.synthetic.main.fragment_detail_poke.*
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.ext.scope

class DetailPokeFragment : DetailPokemonMVIFragment() {
    override val uiStateMachine: UiStateMachine<DetailPokemonState> get() = viewModel
    private val viewModel: DetailPokemonViewModel by viewModel()
    private val currentId: DetailPokeFragmentArgs by navArgs()
    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_detail_poke, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        backList()
        loadPoke(currentId.position)
        scope.launch {
            delay(2000)
            loadSpecie(currentId.position)
        }
    }

    override fun render(state: DetailPokemonState) {
        when (state.stateTypeDetail) {
            is StateTypeDetail.SucessDetailPokemon -> renderSuccessDetail(state)
            is StateTypeDetail.SuccessSpeciePokemon -> renderSuccessSpecie(state)
            is StateTypeDetail.Loanding -> renderLoading(state)
        }
    }

    private fun renderSuccessSpecie(state: DetailPokemonState) {
        val responseSpecie = state.successSpeciePoke
        if (responseSpecie != null) setViewSpecie(responseSpecie)
        progressBarSpecie.visibility = View.GONE

    }

    private fun renderSuccessDetail(state: DetailPokemonState) {
        val responsePokemon = state.successDetailPoke
        progressBarPoke1.visibility = View.GONE
        progressBarPoke.visibility = View.GONE
        if (responsePokemon != null) setView(responsePokemon)
    }

    private fun renderLoading(state: DetailPokemonState) {
        if (state.successDetailPoke == null) {
            progressBarPoke1.visibility = View.VISIBLE
            progressBarPoke.visibility = View.VISIBLE
        }
        if (state.successSpeciePoke == null) {
            progressBarSpecie.visibility = View.VISIBLE
        }
    }

    private fun loadPoke(idPoke: Int) {
        val id = idPoke + 1
        image_view_poke.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$id.png")
        viewModel.mutate(
            DetailPokemonAction.DetailPokemonResquestAction(id.toString())
        )
    }

    private fun loadSpecie(idPoke: Int) {
        val id = idPoke + 1
        viewModel.mutate(
            DetailPokemonAction.SpeciePokemonRequestAction(id.toString())
        )
    }

    private fun setView(responsePokemon: Pokemon) {
        text_view_name.text = responsePokemon.name
        val types = responsePokemon.types
        if (types.size == 2) {
            text_view_type.text = types[0].type.name
            text_view_type2.text = types[1].type.name
        } else text_view_type.text = types[0].type.name
        text_view_number.text = "#${responsePokemon.id.toString()}"
        text_view_height.text = responsePokemon.height.toString()
        text_view_weight.text = responsePokemon.weight.toString()
        if (responsePokemon.abilities.size == 2) {
            text_view_ability1.text = responsePokemon.abilities[0].ability.name
            text_view_ability2.text = responsePokemon.abilities[1].ability.name
        } else {
            text_view_ability1.text = responsePokemon.abilities[0].ability.name
            text_view_ability2.visibility = View.GONE
        }
    }

    private fun setViewSpecie(responseSpecie: PokemonSpecies) {
        val type = responseSpecie.flavorTextEntries[0].flavorText
        text_view_flavor.text = type
    }

    private fun Any.toast(duration: Int = Toast.LENGTH_LONG): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

//    private fun backList() {
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController().navigate(R.id.action_detailPokeFragment_to_mainFragment)
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(callback)
//    }
}