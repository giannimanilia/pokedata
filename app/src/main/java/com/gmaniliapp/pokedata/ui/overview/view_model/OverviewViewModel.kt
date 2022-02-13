package com.gmaniliapp.pokedata.ui.overview.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmaniliapp.pokedata.data.PokemonRepository
import com.gmaniliapp.pokedata.data.model.Pokemon
import com.gmaniliapp.pokedata.data.model.PokemonApiStatus
import com.gmaniliapp.pokedata.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus>
        get() = _status

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>>
        get() = _pokemons

    private val retrievedPokemons: ArrayList<Pokemon> = ArrayList()

    private var limit = 20
    private var offset = 0

    private var allLoaded = false

    fun getPokemons() {
        viewModelScope.launch {
            _status.value = PokemonApiStatus.LOADING

            val result = pokemonRepository.getPokemons(limit = limit, offset = offset)

            _status.value = PokemonApiStatus.DONE

            result.let {
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let {
                            retrievedPokemons.addAll(it.results)

                            _pokemons.postValue(retrievedPokemons)

                            allLoaded = it.next == null
                            offset += it.results.count()
                        }
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                }
            }
        }
    }

}