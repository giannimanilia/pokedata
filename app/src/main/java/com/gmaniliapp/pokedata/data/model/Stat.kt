package com.gmaniliapp.pokedata.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stat(
    val name: String,
    val url: String) : Parcelable