package com.example.pokedex.abilitylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvi.UiStateMachine
import com.example.pokedex.R
import com.example.pokedex.adapter.AbilityAdapter
import com.example.pokedex.adapter.MainAdapter
import com.example.pokedex.model.PokemonResult
import kotlinx.android.synthetic.main.fragment_ability_list.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AbilityListFragment : AbilityMVIFragment(), AbilityAdapter.OnClickAbilityListener {

    override val uiStateMachine: UiStateMachine<AbilityState> get() = viewModel
    private val viewModel: AbilityListViewModel by viewModel()
    var listAbility: ArrayList<PokemonResult> = arrayListOf()
    var adapter : AbilityAdapter = AbilityAdapter(this)
    lateinit var layout: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ability_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData(0)
        putAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun render(state: AbilityState) {
        when(state.stateTypeAbility){
            is StateTypeAbility.SuccessListAbility -> renderSuccessListAbility(state)
        }
    }

    private fun putAdapter() {
        adapter = AbilityAdapter(this)
        layout = LinearLayoutManager(context)
        recycler_view_ability.adapter = adapter
        recycler_view_ability.layoutManager = layout
        progressBar_ability.visibility = View.INVISIBLE
    }

    private fun renderSuccessListAbility(state: AbilityState){
        val responses = state.successListAbility!!
//        listAbility = responses.results
        adapter.addListAbility(responses.results)
    }

    private fun loadData(offset: Int){
        viewModel.mutate(
            AbilityAction.ListAbilityRequestAction(20, offset)
        )
    }

    override fun abilityClick(position: Int) {

    }


}