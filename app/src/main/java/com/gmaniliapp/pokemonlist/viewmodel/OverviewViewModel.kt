package com.gmaniliapp.pokemonlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmaniliapp.pokemonlist.data.model.Pokemon
import com.gmaniliapp.pokemonlist.data.remote.PokemonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class PokemonApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    // Handle the status of the most recent request
    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus>
        get() = _status

    // Handle list of pokemons
    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>>
        get() = _pokemons

    // Handle navigation to the selected pokemon
    private val _navigateToSelectedPokemon = MutableLiveData<Pokemon>()
    val navigateToSelectedPokemon: LiveData<Pokemon>
        get() = _navigateToSelectedPokemon

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getPokemons()
    }

    /**
     * Gets pokemons from the Pokemon API Retrofit service and
     * updates the [Pokemon] [List] and [PokemonApiStatus] [LiveData]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     */
    private fun getPokemons() {
        coroutineScope.launch {
            val getPokemonsDeferred = PokemonApi.retrofitService.getPokemonsAsync()
            try {
                _status.value =
                    PokemonApiStatus.LOADING
                val listResult = getPokemonsDeferred.await()
                _status.value =
                    PokemonApiStatus.DONE
                _pokemons.value = listResult.results
            } catch (e: Exception) {
                _status.value =
                    PokemonApiStatus.ERROR
                _pokemons.value = ArrayList()
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

    /**
     * When the pokemon is clicked, set the [_navigateToSelectedPokemon] [MutableLiveData]
     * @param pokemon The [Pokemon] that was clicked on.
     */
    fun displayPokemonDetails(pokemon: Pokemon) {
        _navigateToSelectedPokemon.value = pokemon
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedPokemon is set to null
     */
    fun displayPokemonDetailsComplete() {
        _navigateToSelectedPokemon.value = null
    }
}