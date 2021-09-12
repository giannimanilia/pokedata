package com.gmaniliapp.pokedata.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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

class DetailViewModel(pokemon: Pokemon, app: Application) : AndroidViewModel(app) {

    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus>
        get() = _status

    private val _selectedPokemon = MutableLiveData<Pokemon>()
    val selectedPokemon: LiveData<Pokemon>
        get() = _selectedPokemon

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _selectedPokemon.value = pokemon
        getPokemonDetails()
    }

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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
