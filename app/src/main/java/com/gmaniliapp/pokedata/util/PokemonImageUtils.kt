package com.gmaniliapp.pokedata.util

import android.net.Uri
import androidx.core.net.toUri
import com.gmaniliapp.pokedata.common.PokemonImageConstants

fun generatePokemonImageUriFromPokemonUrl(pokemonUrl: String): Uri {
    val uri: Uri = Uri.parse(pokemonUrl)
    val id: String? = uri.lastPathSegment
    return generatePokemonImageUriFromPokemonId(id)
}

fun generatePokemonImageUriFromPokemonId(pokemonId: String?): Uri {
    return ("${PokemonImageConstants.POKEMON_IMAGE_BASE_URL}$pokemonId${PokemonImageConstants.POKEMON_IMAGE_EXTENSION}").toUri()
        .buildUpon()
        .scheme("https").build()
}