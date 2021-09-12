package com.gmaniliapp.pokedata.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmaniliapp.pokedata.databinding.GridViewItemBinding
import com.gmaniliapp.pokedata.data.model.Pokemon

class PokemonGridAdapter(val onClickListener: OnClickListener) :
        ListAdapter<Pokemon, PokemonGridAdapter.PokemonViewHolder>(
            DiffCallback
        ) {

    class PokemonViewHolder(private var binding: GridViewItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.pokemon = pokemon
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            GridViewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pokemon)
        }
        holder.bind(pokemon)
    }

    class OnClickListener(val clickListener: (pokemon: Pokemon) -> Unit) {
        fun onClick(pokemon: Pokemon) = clickListener(pokemon)
    }
}
