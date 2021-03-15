package com.example.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.R
import com.example.pokedex.model.PokemonResponse
import com.example.pokedex.model.PokemonResult
import com.example.pokedex.ui.MainFragment
import kotlinx.android.synthetic.main.rv_list.view.*
import org.w3c.dom.Text

class MainAdapter(var listener: OnClickPokeListener) :
    RecyclerView.Adapter<MainAdapter.ListPokeAdapterViewHolder>() {

    var list = arrayListOf<PokemonResult>()
    var listcomplete: ArrayList<PokemonResult> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.ListPokeAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_list, parent, false)
        return ListPokeAdapterViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    fun addListPoke(lista: ArrayList<PokemonResult>) {
        list.plusAssign(lista)
        list.addAll(listcomplete)
        notifyDataSetChanged()
//        notifyItemInserted(position-20)
//        notifyItemRangeInserted()
    }
    override fun onBindViewHolder(
        holder: MainAdapter.ListPokeAdapterViewHolder,
        position: Int
    ) {
        val poke = list[position]
        holder.namePoke.text = poke.name
        holder.numberPoke.text = "#${position+1}"
        holder.imagePoke.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/${position+1}.png")

    }

    interface OnClickPokeListener {
        fun pokeClick(position: Int)
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
                listener.pokeClick(position)
        }
    }
}