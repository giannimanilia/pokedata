package com.gmaniliapp.pokemonlist.data.remote

import com.gmaniliapp.pokemonlist.data.model.Pokemon
import com.gmaniliapp.pokemonlist.data.model.PokemonsResult
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://pokeapi.co/api/v2/"

/**
 * Build the Moshi object that Retrofit will be using
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with the Moshi object
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the API methods
 */
interface PokemonApiService {
    @GET("pokemon")
    fun getPokemonsAsync():
            Deferred<PokemonsResult>

    @GET("pokemon/{id}")
    fun getPokemonDetailsAsync(@Path("id") id: String):
            Deferred<Pokemon>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object PokemonApi {
    val retrofitService : PokemonApiService by lazy { retrofit.create(
        PokemonApiService::class.java) }
}