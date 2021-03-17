package com.example.pokedex.mvilist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvi.UiStateMachine
import com.example.pokedex.R
import com.example.pokedex.adapter.MainAdapter
import com.example.pokedex.detailpoke.DetailPokeFragmentDirections
import com.example.pokedex.model.PokemonResult
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListPokemonFragment : ListPokemonMVIFragment(), MainAdapter.OnClickPokeListener {
    override val uiStateMachine: UiStateMachine<ListPokemonState> get() = viewModel
    private val viewModel: ListPokemonViewModel by viewModel()
    private var adapter: MainAdapter = MainAdapter(this)
    lateinit var layout: LinearLayoutManager
    private var offset = 0
    var listapoke: ArrayList<PokemonResult> = arrayListOf()
    private val lastVisibleItemPosition: Int get() = layout.findLastVisibleItemPosition()
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    lateinit var pokemon: String
    private var idPoke: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        offset = 0
        loadData(0)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewScrollListener()
        searchPoke()
        reload()
//        if(listapoke.size == 0){
            putAdapter()
//        }
    }

    override fun render(state: ListPokemonState) {
        when (state.stateType) {
            is StateType.Loanding -> renderLoadListState()
            is StateType.ErrorList -> renderErrorListState()
            is StateType.ErrorSearch -> renderErrorSearchState()
            is StateType.SuccessSearch -> renderSuccesSearch(state)
            is StateType.SuccessList -> renderSucessList(state)
        }
    }

    private fun renderSucessList(state: ListPokemonState) {
        "requisicao".toast()
        val response = state.successList!!
        listapoke = response.results
        adapter.addListPoke(listapoke)
        idPoke = null
        recycler_view.visibility = View.VISIBLE
        button_reload.visibility = View.INVISIBLE
        text_view_error.visibility = View.INVISIBLE
        image_view_error.visibility = View.GONE
        hideLoanding()
    }

    private fun renderSuccesSearch(state: ListPokemonState) {
        val response = state.successSearch!!
        val result = PokemonResult(response.name, "url")
        val resultList: ArrayList<PokemonResult> = arrayListOf(result)
        idPoke = response.id
        adapter.addSearchPoke(resultList, idPoke!!)
        recycler_view.visibility = View.VISIBLE
        button_reload.visibility = View.INVISIBLE
        text_view_error.visibility = View.INVISIBLE
        image_view_error.visibility = View.GONE
        hideLoanding()
    }

    private fun renderErrorSearchState() {
        "pokemon nao encontrado".toast()
        hideLoanding()
        recycler_view.visibility = View.GONE
        image_view_error.visibility = View.VISIBLE
    }

    private fun searchPoke() {
        edit_text_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                    offset = 0
                    adapter.clearListPoke(null)
                    loadData(offset)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.mutate(
                    ListPokemonActions.SearchPokemonRequestAction(query!!)
                )
                return false
            }
        })
    }

    private fun putAdapter() {
        adapter = MainAdapter(this)
        layout = LinearLayoutManager(context)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = layout
        progressBar.visibility = INVISIBLE
    }

    private fun loadData(offset: Int) {
        viewModel.mutate(
            ListPokemonActions.ListPokemonRequestAction(20, offset)
        )
    }

    override fun pokeClick(position: Int, idPoke: Int?) {
        if (idPoke != null) {
            val actionid =
                ListPokemonFragmentDirections.actionMainFragmentToDetailPokeFragment(idPoke - 1)
            findNavController().navigate(actionid)
        } else {
            val actionPostion =
                ListPokemonFragmentDirections.actionMainFragmentToDetailPokeFragment(position)
            findNavController().navigate(actionPostion)
        }
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

    private fun Any.toast(duration: Int = Toast.LENGTH_LONG): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
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

//        edit_text_search.isIconified =false
//        edit_text_search.setOnClickListener(View.OnClickListener {
//            edit_text_search.isIconified(false)
//        })
    }
}
