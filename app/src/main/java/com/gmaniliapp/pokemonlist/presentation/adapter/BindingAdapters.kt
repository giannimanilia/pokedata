package com.gmaniliapp.pokemonlist.presentation.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gmaniliapp.pokemonlist.viewmodel.PokemonApiStatus
import com.gmaniliapp.pokemonlist.R
import com.gmaniliapp.pokemonlist.data.model.Pokemon
import com.gmaniliapp.pokemonlist.data.model.PokemonStat
import com.gmaniliapp.pokemonlist.data.model.PokemonType
import java.net.URI

private const val BASE_IMAGE_URL = "https://pokeres.bastionbot.org/images/pokemon/"

/**
 * Bind pokemons
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Pokemon>?) {
    val adapter = recyclerView.adapter as PokemonGridAdapter
    adapter.submitList(data)
}

/**
 * Bind pokemon's types
 */
@BindingAdapter("pokemonTypesData")
fun bindPokemonTypes(recyclerView: RecyclerView, data: List<PokemonType>?) {
    val adapter = recyclerView.adapter as PokemonTypesAdapter
    adapter.submitList(data)
}

/**
 * Bind pokemon's stats
 */
@BindingAdapter("pokemonStatsData")
fun bindPokemonStats(recyclerView: RecyclerView, data: List<PokemonStat>?) {
    val adapter = recyclerView.adapter as PokemonStatsAdapter
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, url: String?) {
    url?.let {
        val uri = URI(url)
        val segments: Array<String> = uri.path.split("/").toTypedArray()
        val id = segments[segments.size - 2]
        val imgUri = ("$BASE_IMAGE_URL$id.png").toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}

/**
 * This binding adapter displays the [PokemonApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
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
