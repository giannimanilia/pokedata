package com.gmaniliapp.pokedata.presentation.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gmaniliapp.pokedata.R
import com.gmaniliapp.pokedata.data.model.Pokemon
import com.gmaniliapp.pokedata.data.model.PokemonApiStatus
import com.gmaniliapp.pokedata.data.model.PokemonStat
import com.gmaniliapp.pokedata.data.model.PokemonType

private const val BASE_IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Pokemon>?) {
    val adapter = recyclerView.adapter as PokemonGridAdapter
    adapter.submitList(data)
    adapter.notifyDataSetChanged()
}

@BindingAdapter("pokemonTypesData")
fun bindPokemonTypes(recyclerView: RecyclerView, data: List<PokemonType>?) {
    val adapter = recyclerView.adapter as PokemonTypesAdapter
    adapter.submitList(data)
}

@BindingAdapter("pokemonStatsData")
fun bindPokemonStats(recyclerView: RecyclerView, data: List<PokemonStat>?) {
    val adapter = recyclerView.adapter as PokemonStatsAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, url: String?) {
    url?.let {
        val uri: Uri = Uri.parse(url)
        val id: String? = uri.lastPathSegment
        val imgUri = ("$BASE_IMAGE_URL$id.png").toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("pokemonApiStatus")
fun bindStatus(statusImageView: ImageView, status: PokemonApiStatus?) {
    when (status) {
        PokemonApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        PokemonApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        PokemonApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
