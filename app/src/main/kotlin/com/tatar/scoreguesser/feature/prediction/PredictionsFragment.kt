package com.tatar.scoreguesser.feature.prediction

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatar.presentation.feature.prediction.PredictionsViewModel
import com.tatar.presentation.util.EventObserver
import com.tatar.presentation.viewmodel.factory.withFactory
import com.tatar.scoreguesser.R
import com.tatar.scoreguesser.ScoreGuesserApplication
import com.tatar.scoreguesser.base.BaseFragment
import com.tatar.scoreguesser.databinding.FragmentPredictionsBinding
import com.tatar.scoreguesser.util.mapDistinct
import javax.inject.Inject

class PredictionsFragment : BaseFragment(R.layout.fragment_predictions),
    PredictionDialogFragment.PredictionDialogListener {

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
        observeEvents()
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

        viewModel.state.mapDistinct { it.isResultsButtonVisible }
            .observe(viewLifecycleOwner, { binding.resultsTv.isVisible = it })
    }

    private fun observeEvents() {
        viewModel.showPredictionDialog.observe(viewLifecycleOwner, EventObserver {
            val dialog = PredictionDialogFragment(it)
            dialog.setTargetFragment(this, 13)
            dialog.show(parentFragmentManager, "PredictionDialogFragment")
        })
    }

    private fun bindInteractions(binding: FragmentPredictionsBinding) {
        binding.resultsTv.setOnClickListener { viewModel.onResultsClicked() }
        binding.errorTv.setOnClickListener { viewModel.makePredictionsCall() }
    }

    override fun onPredictionsEntered(
        matchIdentifier: String,
        homeTeamScore: String,
        awayTeamScore: String
    ) {
        viewModel.onPredictionsApplied(
            matchIdentifier,
            homeTeamScore,
            awayTeamScore
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onViewPaused()
    }
}
