package com.gmaniliapp.pokedata.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonType(
    val slot: Int,
    val type: Type) : Parcelable