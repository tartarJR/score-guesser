package com.tatar.scoreguesser.feature.result

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatar.presentation.feature.result.ResultsViewModel
import com.tatar.scoreguesser.R
import com.tatar.scoreguesser.ScoreGuesserApplication
import com.tatar.scoreguesser.base.BaseFragment
import com.tatar.scoreguesser.databinding.FragmentResultsBinding
import com.tatar.scoreguesser.util.mapDistinct
import javax.inject.Inject

class ResultsFragment : BaseFragment(R.layout.fragment_results) {

    @Inject
    lateinit var resultsAdapter: ResultsAdapter

    override val viewModel: ResultsViewModel by viewModels { viewModelFactory }

    override fun provideDependencies() {
        DaggerResultsComponent.factory()
            .create((requireActivity().application as ScoreGuesserApplication).appComponent)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentResultsBinding.bind(view)

        setUpView(binding)
        observeState(binding)
        bindInteractions(binding)
    }

    private fun setUpView(binding: FragmentResultsBinding) {
        binding.resultsRv.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = resultsAdapter
        }
    }

    private fun observeState(binding: FragmentResultsBinding) {
        viewModel.state.mapDistinct { it.isLoading }
            .observe(viewLifecycleOwner, { binding.progressBar.isVisible = it })

        viewModel.state.mapDistinct { it.isContentVisible }
            .observe(viewLifecycleOwner, { binding.resultsRv.isVisible = it })

        viewModel.state.mapDistinct { it.isError }
            .observe(viewLifecycleOwner, { binding.errorTv.isVisible = it })

        viewModel.state.mapDistinct { it.isEmpty }
            .observe(viewLifecycleOwner, { binding.noItemsTv.isVisible = it })

        viewModel.state.mapDistinct { it.results }
            .observe(viewLifecycleOwner, { resultsAdapter.submitList(it) })
    }

    private fun bindInteractions(binding: FragmentResultsBinding) {
        binding.restartTv.setOnClickListener { viewModel.onRestartClicked() }
        binding.errorTv.setOnClickListener { viewModel.onTryAgainClicked() }
    }
}
