package com.example.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvi.UiStateMachine
import com.example.pokedex.R
import com.example.pokedex.adapter.MainAdapter
import com.example.pokedex.model.PokemonResponse
import com.example.pokedex.model.PokemonResult
import com.example.pokedex.mvilist.*
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONArray
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.Array.get


class MainFragment : ListPokemonMVIFragment(), MainAdapter.OnClickPokeListener {
    override val uiStateMachine: UiStateMachine<ListPokemonState> get() = viewModel
    private val viewModel: ListPokemonViewModel by viewModel()
    lateinit var adapter: MainAdapter
    lateinit var layout: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutate(
            ListPokemonActions.ListPokemonRequestAction()
        )
        adapter = MainAdapter(this, this)
        layout = GridLayoutManager(context, 2)
        rv.adapter = adapter
        rv.layoutManager = layout
        rv.hasFixedSize()
        val teste1: PokemonResult = PokemonResult("teste", "outro teste")
        val teste2: PokemonResult = PokemonResult("teste1", "outro teste1")
        val list : ArrayList<PokemonResult> = arrayListOf(teste1, teste2)
        adapter.addListPoke(list)
    }

    override fun render(state: ListPokemonState) {
        when(state.stateType){
            is StateType.SuccessList -> renderSucessList(state)
        }
    }

    private fun renderSucessList(state: ListPokemonState){
        "teste".toast()

        val response = state.successList!!
        response.results
    }

    override fun pokeClick(position: Int) {

    }
    private fun Any.toast(duration: Int = Toast.LENGTH_LONG): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }
}

