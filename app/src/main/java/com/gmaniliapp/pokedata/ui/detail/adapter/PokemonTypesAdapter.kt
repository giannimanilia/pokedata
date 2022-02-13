package com.gmaniliapp.pokedata.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmaniliapp.pokedata.data.model.PokemonType
import com.gmaniliapp.pokedata.databinding.LayoutPokemonTypesBinding

class PokemonTypesAdapter : RecyclerView.Adapter<PokemonTypesAdapter.PokemonTypeViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PokemonType>() {
        override fun areItemsTheSame(oldItem: PokemonType, newItem: PokemonType): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PokemonType, newItem: PokemonType): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var pokemonTypes: List<PokemonType>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonTypeViewHolder {
        val binding =
            LayoutPokemonTypesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonTypeViewHolder, position: Int) {
        val pokemonType = pokemonTypes[position]
        holder.bind(pokemonType)
    }

    override fun getItemCount(): Int {
        return pokemonTypes.size
    }

    class PokemonTypeViewHolder(private val binding: LayoutPokemonTypesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonType: PokemonType) {
            binding.pokemonTypeName.text = pokemonType.type.name
        }
    }
}
