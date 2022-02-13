package com.gmaniliapp.pokedata.ui.overview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gmaniliapp.pokedata.R
import com.gmaniliapp.pokedata.databinding.FragmentOverviewBinding
import com.gmaniliapp.pokedata.ui.detail.fragment.DetailFragment
import com.gmaniliapp.pokedata.ui.overview.adapter.PokemonOverviewAdapter
import com.gmaniliapp.pokedata.ui.overview.view_model.OverviewViewModel
import com.gmaniliapp.pokedata.util.PokemonItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private lateinit var pokemonOverviewAdapter: PokemonOverviewAdapter

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OverviewViewModel by viewModels()

    private var twoPane: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        if (binding.itemDetailContainer != null) {
            twoPane = true
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPokemons()

        setupPokemons()

        subscribeToObservers()
    }

    private fun setupPokemons() {
        pokemonOverviewAdapter = PokemonOverviewAdapter()
        pokemonOverviewAdapter.setOnItemClickListener { navigateToDetailFragment(it.name) }

        binding.pokemons.adapter = pokemonOverviewAdapter

        binding.pokemons.addItemDecoration(
            PokemonItemDecoration(
                resources.getDimension(R.dimen.cardview_margin).toInt()
            )
        )

        binding.pokemons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getPokemons()
                }
            }
        })
    }

    private fun subscribeToObservers() {
        viewModel.pokemons.observe(viewLifecycleOwner, Observer {
            it?.let {
                pokemonOverviewAdapter.pokemons = it
                pokemonOverviewAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun navigateToDetailFragment(name: String) {
        if (twoPane) {
            val detailFragment: Fragment = DetailFragment()
            val arguments = Bundle()
            arguments.putString("id", name)
            detailFragment.arguments = arguments
            val transaction: FragmentTransaction =
                childFragmentManager.beginTransaction()
            transaction.replace(R.id.itemDetailContainer, detailFragment).commit()
        } else {
            findNavController().navigate(
                OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(name)
            )
        }
    }
}
