package com.example.pokedex.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import okhttp3.internal.notifyAll
import org.json.JSONArray
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.Array.get


class MainFragment : ListPokemonMVIFragment(), MainAdapter.OnClickPokeListener {
    override val uiStateMachine: UiStateMachine<ListPokemonState> get() = viewModel
    private val viewModel: ListPokemonViewModel by viewModel()
    private var adapter: MainAdapter = MainAdapter(this)
    lateinit var layout: LinearLayoutManager
    private var offset = 0
    var listcomplete: ArrayList<PokemonResult> = arrayListOf()
    var listapoke: ArrayList<PokemonResult> = arrayListOf()
    private val lastVisibleItemPosition: Int get() = layout.findLastVisibleItemPosition()
//    private val lastItem: Int get() = layout.find
    private lateinit var scrollListener: RecyclerView.OnScrollListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData(0)
        setRecyclerViewScrollListener()
        putAdapter(listapoke)
        reload()
    }

    override fun onResume() {
        super.onResume()
        offset = 0
        listcomplete = arrayListOf()
//        viewModel.mutate(
//            ListPokemonActions.ListPokemonRequestAction(20, 0)
//        )
    }

    override fun render(state: ListPokemonState) {
        when (state.stateType) {
            is StateType.ErrorList -> renderErrorListState()
            is StateType.Loanding -> renderLoadListState()
            else -> renderSucessList(state)
        }
    }

    private fun renderSucessList(state: ListPokemonState) {
        "requisicao".toast()
        val response = state.successList!!
        listapoke = response.results
        adapter.addListPoke(listapoke)
        button_reload.visibility = View.INVISIBLE
        text_view_error.visibility = View.INVISIBLE
        hideLoanding()
    }

    private fun putAdapter(lista: ArrayList<PokemonResult>) {
        adapter = MainAdapter(this)
        layout = LinearLayoutManager(context)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = layout
//        recycler_view.hasFixedSize()
        listcomplete.plusAssign(listapoke)
        adapter.addListPoke(listcomplete)
//        adapter.notifyDataSetChanged()
        progressBar.visibility = INVISIBLE
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recycler_view, newState)
                val totalItemCount = recyclerView!!.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    offset += 20
                    loadData(offset)
                    Log.d("HTTPS", "$offset")
                    Log.d("HTTPS", "Load new list")
                }
            }
        }
        recycler_view.addOnScrollListener(scrollListener)
    }

    private fun loadData(offset: Int) {
        viewModel.mutate(
            ListPokemonActions.ListPokemonRequestAction(20, offset)
        )
    }

    override fun pokeClick(position: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetailPokeFragment(position)
        findNavController().navigate(action)
    }

    private fun Any.toast(duration: Int = Toast.LENGTH_LONG): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    private fun showLoanding() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoanding() {
        progressBar.visibility = View.GONE
    }

    private fun renderLoadListState() {
        showLoanding()
    }

    private fun renderErrorListState() {
        "error".toast()
        hideLoanding()
        text_view_error.visibility = View.VISIBLE
        button_reload.visibility = View.VISIBLE
    }

    private fun reload() {
        button_reload.setOnClickListener {
            loadData(0)
        }
    }
}
