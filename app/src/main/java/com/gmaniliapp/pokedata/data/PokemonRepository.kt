package com.gmaniliapp.pokedata.data

import com.gmaniliapp.pokedata.data.remote.PokemonApi
import com.gmaniliapp.pokedata.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi
) {

    suspend fun getPokemons(limit: Int, offset: Int) = withContext(Dispatchers.IO) {
        try {
            val response = pokemonApi.getPokemons(limit, offset)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.success(body)
                } else {
                    Resource.error(null)
                }
            } else {
                Resource.error(null)
            }
        } catch (exception: Exception) {
            exception.stackTrace
            Resource.error(null)
        }
    }

    suspend fun getPokemonDetails(id: String) = withContext(Dispatchers.IO) {
        try {
            val response = pokemonApi.getPokemonDetails(id)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.success(body)
                } else {
                    Resource.error(null)
                }
            } else {
                Resource.error(null)
            }
        } catch (exception: Exception) {
            exception.stackTrace
            Resource.error(null)
        }
    }

}