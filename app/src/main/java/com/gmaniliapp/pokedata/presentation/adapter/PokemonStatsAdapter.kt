package com.gmaniliapp.pokedata.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmaniliapp.pokedata.data.model.PokemonStat
import com.gmaniliapp.pokedata.databinding.StatsViewItemBinding

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
