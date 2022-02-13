package com.gmaniliapp.pokedata.ui.overview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gmaniliapp.pokedata.R
import com.gmaniliapp.pokedata.data.model.Pokemon
import com.gmaniliapp.pokedata.databinding.LayoutPokemonOverviewBinding
import com.gmaniliapp.pokedata.util.generatePokemonImageUriFromPokemonUrl

class PokemonOverviewAdapter : RecyclerView.Adapter<PokemonOverviewAdapter.PokemonViewHolder>() {

    private var onItemClickListener: ((Pokemon) -> Unit)? = null

    private val diffCallback = object : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var pokemons: List<Pokemon>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            LayoutPokemonOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.bind(pokemon)

        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(pokemon)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    fun setOnItemClickListener(onItemClick: (Pokemon) -> Unit) {
        this.onItemClickListener = onItemClick
    }

    class PokemonViewHolder(private val binding: LayoutPokemonOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.pokemonName.text = pokemon.name

            val pokemonImageUrl = generatePokemonImageUriFromPokemonUrl(pokemon.url)

            Glide.with(binding.pokemonImage.context)
                .load(pokemonImageUrl)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(binding.pokemonImage)
        }
    }
}
