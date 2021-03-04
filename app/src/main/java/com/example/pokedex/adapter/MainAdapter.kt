package com.example.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.model.PokemonResponse
import com.example.pokedex.model.PokemonResult
import com.example.pokedex.ui.MainFragment
import kotlinx.android.synthetic.main.rv_list.view.*

class MainAdapter(var listener: OnClickPokeListener, var context: MainFragment) :
    RecyclerView.Adapter<MainAdapter.ListPokeAdapterViewHolder>() {

    //    val result : PokemonResponse =
    var list = arrayListOf<PokemonResult>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.ListPokeAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_list, parent, false)
        return ListPokeAdapterViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    fun addListPoke(list: ArrayList<PokemonResult>) {
        list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: MainAdapter.ListPokeAdapterViewHolder,
        position: Int
    ) {
        val poke = list[position]
        holder.namePoke.text = poke.name.toString()
        holder.urlPoke.text = poke.url.toString()
    }

    interface OnClickPokeListener {
        fun pokeClick(position: Int)
    }

    inner class ListPokeAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val namePoke: TextView = itemView.text_view_name_pokemon
        val urlPoke: TextView = itemView.url

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position)
                listener.pokeClick(position)
        }
    }
}