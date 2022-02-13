package com.gmaniliapp.pokedata.data.remote

import com.gmaniliapp.pokedata.data.model.Pokemon
import com.gmaniliapp.pokedata.data.model.PokemonsResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<PokemonsResult>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: String): Response<Pokemon>

}