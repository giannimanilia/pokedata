package com.gmaniliapp.pokedata.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmaniliapp.pokedata.data.model.PokemonStat
import com.gmaniliapp.pokedata.databinding.LayoutPokemonStatsBinding

class PokemonStatsAdapter : RecyclerView.Adapter<PokemonStatsAdapter.PokemonStatViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PokemonStat>() {
        override fun areItemsTheSame(oldItem: PokemonStat, newItem: PokemonStat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PokemonStat, newItem: PokemonStat): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var pokemonStats: List<PokemonStat>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonStatViewHolder {
        val binding =
            LayoutPokemonStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonStatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonStatViewHolder, position: Int) {
        val pokemonStat = pokemonStats[position]
        holder.bind(pokemonStat)
    }

    override fun getItemCount(): Int {
        return pokemonStats.size
    }

    class PokemonStatViewHolder(private val binding: LayoutPokemonStatsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonStat: PokemonStat) {
            binding.pokemonStatBase.text =
                String.format(binding.pokemonStatBase.text.toString(), pokemonStat.base_stat)
            binding.pokemonStatEffort.text =
                String.format(binding.pokemonStatEffort.text.toString(), pokemonStat.effort)
            binding.pokemonStatName.text = pokemonStat.stat.name
        }
    }
}
