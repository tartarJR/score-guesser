package com.tatar.scoreguesser.feature.prediction

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatar.presentation.feature.prediction.PredictionsViewModel
import com.tatar.presentation.viewmodel.factory.withFactory
import com.tatar.scoreguesser.R
import com.tatar.scoreguesser.ScoreGuesserApplication
import com.tatar.scoreguesser.base.BaseFragment
import com.tatar.scoreguesser.databinding.FragmentPredictionsBinding
import com.tatar.scoreguesser.util.mapDistinct
import javax.inject.Inject

class PredictionsFragment : BaseFragment(R.layout.fragment_predictions) {

    @Inject
    lateinit var matchesAdapter: MatchesAdapter

    override val viewModel: PredictionsViewModel by viewModels { withFactory(viewModelFactory) }

    override fun provideDependencies() {
        DaggerPredictionsComponent.factory()
            .create((requireActivity().application as ScoreGuesserApplication).appComponent)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPredictionsBinding.bind(view)

        setUpView(binding)
        observeState(binding)
        observeEvents(binding)
        bindInteractions(binding)
    }

    private fun setUpView(binding: FragmentPredictionsBinding) {
        binding.matchesRv.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
            matchesAdapter.setAdapterItemClickListener { viewModel.onMatchesItemClick(it) }
            adapter = matchesAdapter
        }
    }

    private fun observeState(binding: FragmentPredictionsBinding) {
        viewModel.state.mapDistinct { it.isLoading }
            .observe(viewLifecycleOwner, { binding.progressBar.isVisible = it })

        viewModel.state.mapDistinct { it.isContentVisible }
            .observe(viewLifecycleOwner, { binding.matchesRv.isVisible = it })

        viewModel.state.mapDistinct { it.isError }
            .observe(viewLifecycleOwner, { binding.errorTv.isVisible = it })

        viewModel.state.mapDistinct { it.isEmpty }
            .observe(viewLifecycleOwner, { binding.noItemsTv.isVisible = it })

        viewModel.state.mapDistinct { it.matches }
            .observe(viewLifecycleOwner, { matchesAdapter.submitList(it) })
    }

    private fun observeEvents(binding: FragmentPredictionsBinding) {

    }

    private fun bindInteractions(binding: FragmentPredictionsBinding) {
        binding.resultsTv.setOnClickListener { viewModel.onResultsClicked() }
    }
}
