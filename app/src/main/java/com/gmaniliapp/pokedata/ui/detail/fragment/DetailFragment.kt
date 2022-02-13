package com.gmaniliapp.pokedata.ui.detail.fragment

import android.net.Uri
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gmaniliapp.pokedata.R
import com.gmaniliapp.pokedata.data.model.PokemonStat
import com.gmaniliapp.pokedata.data.model.PokemonType
import com.gmaniliapp.pokedata.databinding.FragmentDetailBinding
import com.gmaniliapp.pokedata.ui.detail.adapter.PokemonStatsAdapter
import com.gmaniliapp.pokedata.ui.detail.adapter.PokemonTypesAdapter
import com.gmaniliapp.pokedata.ui.detail.view_model.DetailViewModel
import com.gmaniliapp.pokedata.util.HorizontalItemDecoration
import com.gmaniliapp.pokedata.util.generatePokemonImageUriFromPokemonUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var pokemonTypesAdapter: PokemonTypesAdapter

    private lateinit var pokemonStatsAdapter: PokemonStatsAdapter

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.id.isNotEmpty()) {
            viewModel.getPokemonDetails(args.id)
        }

        setupPokemonTypes()

        setupPokemonStats()

        subscribeToObservers()
    }

    private fun setupPokemonTypes() {
        pokemonTypesAdapter = PokemonTypesAdapter()

        binding.pokemonTypes.adapter = pokemonTypesAdapter

        binding.typesCard.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                binding.pokemonTypes,
                AutoTransition()
            )
            if (binding.pokemonTypes.visibility == View.VISIBLE) {
                binding.pokemonTypes.visibility = View.GONE
                binding.expandArrowTypes.setImageResource(android.R.drawable.arrow_down_float)
            } else {
                binding.pokemonTypes.visibility = View.VISIBLE
                binding.expandArrowTypes.setImageResource(android.R.drawable.arrow_up_float)
            }
        }

        binding.pokemonTypes.addItemDecoration(
            HorizontalItemDecoration(
                resources.getDimension(R.dimen.cardview_margin).toInt()
            )
        )
    }

    private fun setupPokemonStats() {
        pokemonStatsAdapter = PokemonStatsAdapter()

        binding.pokemonStats.adapter = pokemonStatsAdapter

        binding.statsCard.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                binding.pokemonStats,
                AutoTransition()
            )
            if (binding.pokemonStats.visibility == View.VISIBLE) {
                binding.pokemonStats.visibility = View.GONE
                binding.expandArrowStats.setImageResource(android.R.drawable.arrow_down_float)
            } else {
                binding.pokemonStats.visibility = View.VISIBLE
                binding.expandArrowStats.setImageResource(android.R.drawable.arrow_up_float)
            }
        }

        binding.pokemonStats.addItemDecoration(
            HorizontalItemDecoration(
                resources.getDimension(R.dimen.cardview_margin).toInt()
            )
        )
    }

    private fun subscribeToObservers() {
        viewModel.pokemon.observe(viewLifecycleOwner, {
            it?.let {
                populatePokemonImage(generatePokemonImageUriFromPokemonUrl(it.id))
                populateTypesAdapter(it.types)
                populateStatsAdapter(it.stats)
            }
        })
    }

    private fun populateTypesAdapter(pokemonTypes: List<PokemonType>) {
        pokemonTypesAdapter.pokemonTypes = pokemonTypes
        pokemonTypesAdapter.notifyDataSetChanged()
    }

    private fun populateStatsAdapter(pokemonStats: List<PokemonStat>) {
        pokemonStatsAdapter.pokemonStats = pokemonStats
        pokemonStatsAdapter.notifyDataSetChanged()
    }


    private fun populatePokemonImage(pokemonImageUrl: Uri) {
        Glide.with(binding.pokemonImage.context)
            .load(pokemonImageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(binding.pokemonImage)
    }
}