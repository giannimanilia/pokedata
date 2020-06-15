package com.gmaniliapp.pokedata.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val name: String,
    var url: String = "",
    var stats: List<PokemonStat> = listOf(),
    var types: List<PokemonType> = listOf()) : Parcelable