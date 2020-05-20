package com.gmaniliapp.pokemonlist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmaniliapp.pokemonlist.data.model.PokemonType
import com.gmaniliapp.pokemonlist.databinding.TypesViewItemBinding

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
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

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [PokemonType]
     * has been updated.
     */
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
