package com.example.pokedex.detailpoke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mvi.UiStateMachine
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon
import com.example.pokedex.mvilist.ListPokemonState
import com.example.pokedex.mvilist.StateType
import kotlinx.android.synthetic.main.fragment_detail_poke.*
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPokeFragment : DetailPokemonMVIFragment() {
    override val uiStateMachine: UiStateMachine<DetailPokemonState>get()=viewModel
    private val viewModel : DetailPokemonViewModel by viewModel()
    private val currentId: DetailPokeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_poke, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPoke(currentId.position)
    }


    override fun render(state: DetailPokemonState) {
        when(state.stateTypeDetail){
            is StateTypeDetail.SucessDetailPokemon -> renderSuccessDetail(state)
        }
    }
    private fun renderSuccessDetail(state: DetailPokemonState){
        val response = state.successDetailPoke
        if(response != null ) setView(response)
    }

    private fun putViews(){
        val id = currentId.position+1
        image_view_poke.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$id.png")
    }

    private fun loadPoke(idPoke: Int){
        val id = idPoke+1
        image_view_poke.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$id.png")
        viewModel.mutate(
            DetailPokemonAction.DetailPokemonResquestAction(id.toString())
        )
    }

    private fun setView(response: Pokemon){
        text_view_name.text = response.name
        val types = response.types
        if(types.size == 2){
            text_view_type.text = types[0].type.name
            text_view_type2.text = types[1].type.name
        } else text_view_type.text = types[0].type.name
        text_view_number.text = "#${response.id.toString()}"
    }

}