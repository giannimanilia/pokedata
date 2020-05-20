package com.gmaniliapp.pokemonlist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmaniliapp.pokemonlist.data.model.PokemonStat
import com.gmaniliapp.pokemonlist.databinding.StatsViewItemBinding

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class PokemonStatsAdapter :
        ListAdapter<PokemonStat, PokemonStatsAdapter.PokemonStatsViewHolder>(
            DiffCallback
        ) {

    class PokemonStatsViewHolder(private var binding: StatsViewItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonStat: PokemonStat) {
            binding.pokemonStat = pokemonStat
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [PokemonStat]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<PokemonStat>() {
        override fun areItemsTheSame(oldItem: PokemonStat, newItem: PokemonStat): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PokemonStat, newItem: PokemonStat): Boolean {
            return oldItem.stat.name == newItem.stat.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PokemonStatsViewHolder {
        return PokemonStatsViewHolder(
            StatsViewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: PokemonStatsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
