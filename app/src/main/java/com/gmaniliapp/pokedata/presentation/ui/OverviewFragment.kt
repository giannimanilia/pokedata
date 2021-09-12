package com.gmaniliapp.pokedata.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gmaniliapp.pokedata.R
import com.gmaniliapp.pokedata.databinding.FragmentOverviewBinding
import com.gmaniliapp.pokedata.presentation.adapter.PokemonGridAdapter
import com.gmaniliapp.pokedata.utils.PokemonItemDecoration
import com.gmaniliapp.pokedata.viewmodel.OverviewViewModel

class OverviewFragment : Fragment() {

    private var twoPane: Boolean = false

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.pokemonGrid.adapter =
            PokemonGridAdapter(
                PokemonGridAdapter.OnClickListener {
                    viewModel.displayPokemonDetails(it)
                })
        binding.pokemonGrid.addItemDecoration(PokemonItemDecoration(
            resources.getDimension(R.dimen.cardview_margin).toInt()))
        binding.pokemonGrid.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getPokemons()
                }
            }
        })

        if (binding.itemDetailContainer != null) {
            twoPane = true
        }

        viewModel.navigateToSelectedPokemon.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                if (twoPane) {
                    val detailFragment: Fragment = DetailFragment()
                    val arguments = Bundle()
                    arguments.putParcelable("selectedPokemon", it)
                    detailFragment.arguments = arguments
                    val transaction: FragmentTransaction =
                        childFragmentManager.beginTransaction()
                    transaction.replace(R.id.item_detail_container, detailFragment).commit()
                }
                else {
                    this.findNavController().navigate(
                        OverviewFragmentDirections.actionShowDetail(
                            it
                        )
                    )
                }
                viewModel.displayPokemonDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }
}
