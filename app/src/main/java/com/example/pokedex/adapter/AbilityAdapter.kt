package com.example.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.model.PokemonResult
import kotlinx.android.synthetic.main.rv_ability.view.*

class AbilityAdapter(var listener: OnClickAbilityListener) :
    RecyclerView.Adapter<AbilityAdapter.ListAbilityAdapterViewHolder>() {

    var listAbility = arrayListOf<PokemonResult>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbilityAdapter.ListAbilityAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_list, parent, false)
        return ListAbilityAdapterViewHolder(itemView)
    }

    fun addListAbility(lista: ArrayList<PokemonResult>) {
        listAbility.addAll(lista)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listAbility.size

    override fun onBindViewHolder(holder: AbilityAdapter.ListAbilityAdapterViewHolder, position: Int) {
        val ability = listAbility[position]
        holder.nameAbility.text = ability.name
    }

    interface OnClickAbilityListener {
        fun abilityClick(position: Int)
    }

    inner class ListAbilityAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val nameAbility: TextView = itemView.text_view_ability_name
//        val textAbility = itemView.text_view_ability_text

        init{
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.abilityClick(position)
            }
        }

    }
}