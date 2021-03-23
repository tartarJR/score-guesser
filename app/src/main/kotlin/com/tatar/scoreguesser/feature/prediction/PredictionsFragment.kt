package com.tatar.scoreguesser.feature.prediction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tatar.presentation.feature.prediction.PredictionsViewModel
import com.tatar.presentation.viewmodel.factory.withFactory
import com.tatar.scoreguesser.R
import com.tatar.scoreguesser.ScoreGuesserApplication
import com.tatar.scoreguesser.base.BaseFragment
import com.tatar.scoreguesser.databinding.FragmentPredictionsBinding

class PredictionsFragment : BaseFragment(R.layout.fragment_predictions) {

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

    }

    private fun observeState(binding: FragmentPredictionsBinding) {

    }

    private fun observeEvents(binding: FragmentPredictionsBinding) {

    }

    private fun bindInteractions(binding: FragmentPredictionsBinding) {
        binding.resultsTv.setOnClickListener { viewModel.onResultsClicked() }
    }
}
