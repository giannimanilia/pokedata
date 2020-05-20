package com.gmaniliapp.pokemonlist.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmaniliapp.pokemonlist.data.model.Pokemon

/**
 * Simple ViewModel factory that provides the Pokemon and context to the ViewModel.
 */
class DetailViewModelFactory(
    private val pokemon: Pokemon,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(pokemon, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
