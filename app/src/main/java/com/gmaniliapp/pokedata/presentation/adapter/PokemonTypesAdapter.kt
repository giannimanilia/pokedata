package com.gmaniliapp.pokedata.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmaniliapp.pokedata.data.model.PokemonType
import com.gmaniliapp.pokedata.databinding.TypesViewItemBinding

class PokemonTypesAdapter :
        ListAdapter<PokemonType, PokemonTypesAdapter.PokemonTypeViewHolder>(
            DiffCallback
        ) {

    class PokemonTypeViewHolder(private var binding: TypesViewItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonType: PokemonType) {
            binding.pokemonType = pokemonType
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PokemonType>() {
        override fun areItemsTheSame(oldItem: PokemonType, newItem: PokemonType): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PokemonType, newItem: PokemonType): Boolean {
            return oldItem.type.name == newItem.type.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PokemonTypeViewHolder {
        return PokemonTypeViewHolder(
            TypesViewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: PokemonTypeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
