package com.gmaniliapp.pokemonlist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmaniliapp.pokemonlist.data.model.Pokemon
import com.gmaniliapp.pokemonlist.data.model.PokemonApiStatus
import com.gmaniliapp.pokemonlist.data.remote.PokemonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 *  The [ViewModel] associated with the DetailFragment, containing information about the selected
 *  [Pokemon].
 */
class DetailViewModel(pokemon: Pokemon, app: Application) : AndroidViewModel(app) {
    // Handle the status of the most recent request
    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus>
        get() = _status

    // Handle selected pokemon
    private val _selectedPokemon = MutableLiveData<Pokemon>()
    val selectedPokemon: LiveData<Pokemon>
        get() = _selectedPokemon

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _selectedPokemon.value = pokemon
        getPokemonDetails()
    }

    /**
     * Gets pokemon's details from the Pokemon API Retrofit service and
     * updates the [selectedPokemon] and [PokemonApiStatus] [LiveData]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     */
    private fun getPokemonDetails() {
        coroutineScope.launch {
            val getPokemonDetailsDeferred = PokemonApi.retrofitService.getPokemonDetailsAsync(selectedPokemon.value!!.name)
            try {
                _status.value = PokemonApiStatus.LOADING
                val pokemon = getPokemonDetailsDeferred.await()
                pokemon.url = selectedPokemon.value!!.url
                _status.value = PokemonApiStatus.DONE
                _selectedPokemon.value = pokemon
            } catch (e: Exception) {
                _status.value =
                    PokemonApiStatus.ERROR
            }
        }
    }

    /**
     * When the [ViewModel] is finished, cancel the coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
