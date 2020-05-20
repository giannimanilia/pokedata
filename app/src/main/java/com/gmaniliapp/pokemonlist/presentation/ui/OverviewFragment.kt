package com.gmaniliapp.pokemonlist.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gmaniliapp.pokemonlist.viewmodel.OverviewViewModel
import com.gmaniliapp.pokemonlist.presentation.adapter.PokemonGridAdapter
import com.gmaniliapp.pokemonlist.databinding.FragmentOverviewBinding

/**
 * This [Fragment] shows the the status of the Pokemon web services transaction.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize the [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        // Sets the adapter of the pokemonGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when the pokemon is clicked
        binding.pokemonGrid.adapter =
            PokemonGridAdapter(
                PokemonGridAdapter.OnClickListener {
                    viewModel.displayPokemonDetails(it)
                })

        // Observe the navigateToSelectedPokemon LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedPokemon.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionShowDetail(
                        it
                    )
                )
                viewModel.displayPokemonDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }
}
