package com.gmaniliapp.pokedata.ui.overview.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmaniliapp.pokedata.data.model.Pokemon
import com.gmaniliapp.pokedata.data.model.PokemonApiStatus
import com.gmaniliapp.pokedata.data.remote.PokemonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus>
        get() = _status

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>>
        get() = _pokemons

    private val retrievedPokemons: ArrayList<Pokemon> = ArrayList()

    private val _navigateToSelectedPokemon = MutableLiveData<Pokemon>()
    val navigateToSelectedPokemon: LiveData<Pokemon>
        get() = _navigateToSelectedPokemon

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var offset = 0

    private var isLoading = false
    private var allLoaded = false

    init {
        getPokemons()
    }

    fun getPokemons() {
        if (isLoading || allLoaded)
            return

        isLoading = true
        coroutineScope.launch {
            val getPokemonsDeferred = PokemonApi.retrofitService.getPokemonsAsync(offset = offset)
            try {
                _status.value =
                    PokemonApiStatus.LOADING
                val listResult = getPokemonsDeferred.await()
                _status.value =
                    PokemonApiStatus.DONE
                retrievedPokemons.addAll(listResult.results)
                _pokemons.postValue(retrievedPokemons)

                allLoaded = listResult.next == null
                offset += listResult.results.count()
            } catch (e: Exception) {
                _status.value =
                    PokemonApiStatus.ERROR
                _pokemons.value = ArrayList()
            } finally {
                isLoading = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayPokemonDetails(pokemon: Pokemon) {
        _navigateToSelectedPokemon.value = pokemon
    }

    fun displayPokemonDetailsComplete() {
        _navigateToSelectedPokemon.value = null
    }
}