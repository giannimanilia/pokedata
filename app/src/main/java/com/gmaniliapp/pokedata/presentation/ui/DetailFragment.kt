package com.gmaniliapp.pokedata.presentation.ui

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmaniliapp.pokedata.R
import com.gmaniliapp.pokedata.databinding.FragmentDetailBinding
import com.gmaniliapp.pokedata.presentation.adapter.PokemonStatsAdapter
import com.gmaniliapp.pokedata.presentation.adapter.PokemonTypesAdapter
import com.gmaniliapp.pokedata.utils.HorizontalItemDecoration
import com.gmaniliapp.pokedata.viewmodel.DetailViewModel
import com.gmaniliapp.pokedata.viewmodel.DetailViewModelFactory

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

        binding.pokemonTypes.adapter = PokemonTypesAdapter()
        binding.typesCard.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                binding.pokemonTypes,
                AutoTransition()
            )
            if (binding.pokemonTypes.visibility == View.VISIBLE) {
                binding.pokemonTypes.visibility = View.GONE
                binding.expandArrowTypes.setImageResource(android.R.drawable.arrow_down_float)
            }
            else {
                binding.pokemonTypes.visibility = View.VISIBLE
                binding.expandArrowTypes.setImageResource(android.R.drawable.arrow_up_float)
            }
        }
        binding.pokemonTypes.addItemDecoration(
            HorizontalItemDecoration(
            resources.getDimension(R.dimen.cardview_margin).toInt())
        )

        binding.pokemonStats.adapter = PokemonStatsAdapter()
        binding.statsCard.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                binding.pokemonStats,
                AutoTransition()
            )
            if (binding.pokemonStats.visibility == View.VISIBLE) {
                binding.pokemonStats.visibility = View.GONE
                binding.expandArrowStats.setImageResource(android.R.drawable.arrow_down_float)
            }
            else {
                binding.pokemonStats.visibility = View.VISIBLE
                binding.expandArrowStats.setImageResource(android.R.drawable.arrow_up_float)
            }
        }
        binding.pokemonStats.addItemDecoration(
            HorizontalItemDecoration(
                resources.getDimension(R.dimen.cardview_margin).toInt())
        )

        return binding.root
    }
}