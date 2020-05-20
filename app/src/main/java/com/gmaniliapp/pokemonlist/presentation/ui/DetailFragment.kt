package com.gmaniliapp.pokemonlist.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmaniliapp.pokemonlist.databinding.FragmentDetailBinding
import com.gmaniliapp.pokemonlist.presentation.adapter.PokemonGridAdapter
import com.gmaniliapp.pokemonlist.presentation.adapter.PokemonStatsAdapter
import com.gmaniliapp.pokemonlist.presentation.adapter.PokemonTypesAdapter
import com.gmaniliapp.pokemonlist.viewmodel.DetailViewModel
import com.gmaniliapp.pokemonlist.viewmodel.DetailViewModelFactory

/**
 * This [Fragment] shows the detailed information about a selected pokemon.
 */
class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application

        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val pokemon = DetailFragmentArgs.fromBundle(arguments!!).selectedPokemon
        val viewModelFactory = DetailViewModelFactory(pokemon, application)

        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(DetailViewModel::class.java)

        // Sets the adapter of the pokemonTypes RecyclerView
        binding.pokemonTypes.adapter = PokemonTypesAdapter()

        // Sets the adapter of the pokemonStats RecyclerView
        binding.pokemonStats.adapter = PokemonStatsAdapter()

        return binding.root
    }
}