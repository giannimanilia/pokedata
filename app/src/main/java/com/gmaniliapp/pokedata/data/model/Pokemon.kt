package com.gmaniliapp.pokedata.data.model

data class Pokemon(
    val id: String,
    val name: String,
    var url: String = "",
    var stats: List<PokemonStat> = listOf(),
    var types: List<PokemonType> = listOf()
)