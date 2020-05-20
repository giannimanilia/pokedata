package com.gmaniliapp.pokemonlist.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Type(
    val name: String,
    val url: String) : Parcelable