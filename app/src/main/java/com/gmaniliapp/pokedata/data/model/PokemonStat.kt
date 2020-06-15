package com.gmaniliapp.pokedata.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonStat(
    val base_stat: Int,
    val effort: Int,
    val stat: Stat) : Parcelable