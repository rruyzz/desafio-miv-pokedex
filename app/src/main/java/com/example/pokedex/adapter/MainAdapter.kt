package com.example.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonResult
import kotlinx.android.synthetic.main.rv_list.view.*

class MainAdapter(var listener: OnClickPokeListener) :
    RecyclerView.Adapter<MainAdapter.ListPokeAdapterViewHolder>() {

    var list = arrayListOf<PokemonResult>()
    var idPoke: Int? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.ListPokeAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_list, parent, false)
        return ListPokeAdapterViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    fun addListPoke(lista: ArrayList<PokemonResult>) {
        list.addAll(lista)
        notifyDataSetChanged()
    }
    fun clearListPoke(id: Int?) {
        idPoke = id
        list.clear()
        notifyDataSetChanged()
    }
    fun addSearchPoke(pokemonItem: ArrayList<PokemonResult>, id: Int){
        list.clear()
        list.addAll(pokemonItem)
        idPoke = id
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(
        holder: MainAdapter.ListPokeAdapterViewHolder,
        position: Int
    ) {
        val poke = list[position]
        if(idPoke != null) {
            holder.namePoke.text = poke.name
            holder.numberPoke.text = "#"+idPoke.toString()
            holder.imagePoke.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$idPoke.png")
        } else{
            holder.namePoke.text = poke.name
            holder.numberPoke.text = "#${position + 1}"
            holder.imagePoke.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/${position + 1}.png")
        }
    }
    interface OnClickPokeListener {
        fun pokeClick(position: Int, id: Int?)
    }

    inner class ListPokeAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val namePoke: TextView = itemView.text_view_name_pokemon
        val numberPoke: TextView = itemView.text_view_number_pokemon
        val imagePoke: ImageView = itemView.image_view_pokemon

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position)
                listener.pokeClick(position, idPoke)
        }
    }
}