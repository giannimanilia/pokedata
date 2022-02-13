package com.gmaniliapp.pokedata.data.model

data class PokemonsResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon> = listOf()
)