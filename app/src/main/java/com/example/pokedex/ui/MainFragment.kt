package com.example.pokedex.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
    private var offset = 0
    lateinit var layout: LinearLayoutManager
    var isLoading = false
    lateinit var listapoke: ArrayList<PokemonResult>
    lateinit var nextPage: String
    private val lastVisibleItemPosition: Int
        get() = layout.findLastVisibleItemPosition()

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
    }

    override fun onResume() {
        super.onResume()
        offset = 0
        viewModel.mutate(
            ListPokemonActions.ListPokemonRequestAction(20, 0)
        )
    }


    override fun render(state: ListPokemonState) {
        when (state.stateType) {
            is StateType.SuccessList -> renderSucessList(state)
            is StateType.Loanding -> renderLoadListState()
        }
    }

    private fun renderSucessList(state: ListPokemonState) {
        "teste".toast()
        val response = state.successList!!
        listapoke = response.results
        nextPage = response.next.toString()
        putAdapter(listapoke)
//        setRecyclerViewScrollListener()
//        initScrollListener()
    }

    private fun renderLoadListState() {
        showLoanding()
    }

    private fun putAdapter(lista: ArrayList<PokemonResult>) {
        adapter = MainAdapter(this)
        layout = LinearLayoutManager(context)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = layout
        recycler_view.hasFixedSize()
        adapter.addListPoke(lista, offset)
        progressBar.visibility = View.INVISIBLE
    }

    override fun pokeClick(position: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetailPokeFragment(position)
        findNavController().navigate(action)
    }

    private fun Any.toast(duration: Int = Toast.LENGTH_LONG): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    private fun showLoanding(){
        progressBar.visibility = View.VISIBLE
    }

}


//    private fun setRecyclerViewScrollListener() {
//        scrollListener = object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recycler_view, newState)
//                val totalItemCount = recyclerView!!.layoutManager?.itemCount
//                if (totalItemCount == lastVisibleItemPosition + 1) {
//                    offset+=20
//                    loadData(offset)
//                    Log.d("Offset", "$offset")
//                    Log.d("MyTAG", "Load new list")
//                    recycler_view.removeOnScrollListener(scrollListener)
//                }
//            }
//        }
//        recycler_view.addOnScrollListener(scrollListener)
//    }
//
//    private fun loadData(offset: Int) {
//        viewModel.mutate(
//            ListPokemonActions.ListPokemonRequestAction(20, offset)
//        )
//    }


//        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
//
////                if(!isLoading){
//                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listapoke.size - 1) {
//                    viewModel.mutate(
//                        ListPokemonActions.ListPokemonRequestAction(0, offset)
//                    )
////                        isLoading = true
////                    }
//                }
//            }
//        })
//    }


//    private fun loadMore(){
//        listapoke.add(null)
//        recycler_view.notifyAll(ItemInserted(listapoke.size - 1))
//
//        val handler = Handler() // le handler a pour rôle de simuler le temps de chargement: je donne 10 secondes
//        handler.postDelayed(object : Runnable {
//            override fun run() {
//                rowsArrayList.removeAt(rowsArrayList.size - 1)
//                val scrollPosition = rowsArrayList.size
//                recyclerViewAdapter.notifyItemRemoved(scrollPosition)
//
//                var currentSize = scrollPosition
//                val nextLimit = currentSize + 10 // car on ajoute 10 éléments à chaques recharge
//
//                while (currentSize -1 < nextLimit){
//                    rowsArrayList.add("Item $currentSize");
//                    currentSize++;
//                }
//
//                recyclerViewAdapter.notifyDataSetChanged()
//                isLoading =false
//            }
//
//        }, 2000)
//    }

//    private fun initScrollListener() {
//        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (layout != null && layout.findLastCompletelyVisibleItemPosition() ==
//                    listapoke.size -1
//                ) {
//
//                    page+=20
//                    bottom of list!
//                    viewModel.mutate(
//                        ListPokemonActions.ListPokemonRequestAction()
//                    )
//                }
//            }
//        })
//    }

//    private fun setRecyclerViewScrollListener() {
//        scrollListener = object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                val totalItemCount = recyclerView!!.layoutManager?.itemCount
//                if (totalItemCount == lastVisibleItemPosition - 1) {
//                    Log.d("MyTAG", "Load new list")
//                    recycler_view.removeOnScrollListener(scrollListener)
//                    super.onScrollStateChanged(recycler_view, newState)
//                }
//                recycler_view.addOnScrollListener(scrollListener)
//            }
//        }
//    }
//}

//    private fun initScrollListener() {
//        recycler_view.apply {
//            adapter = this.adapter
//            layoutManager = LinearLayoutManager(context)
//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    val visibleItemCount: Int = layoutManager?.childCount!!
//                    val totalItemCount: Int = layoutManager?.itemCount!!
//                    val pastVisibleItems: Int =
//                        (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
//                    if (pastVisibleItems + visibleItemCount >= totalItemCount) {
//                        loadData(adapter!!.itemCount)
//                    }
//                }
//            })
//        }
//    }
