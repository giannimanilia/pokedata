package com.gmaniliapp.pokemonlist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmaniliapp.pokemonlist.data.model.Pokemon

/**
 *  The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [Pokemon].
 */
class DetailViewModel(pokemon: Pokemon, app: Application) : AndroidViewModel(app) {
    private val _selectedPokemon = MutableLiveData<Pokemon>()
    val selectedPokemon: LiveData<Pokemon>
        get() = _selectedPokemon

    init {
        _selectedPokemon.value = pokemon
    }
}
