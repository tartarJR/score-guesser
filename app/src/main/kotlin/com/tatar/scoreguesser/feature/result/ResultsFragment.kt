package com.tatar.scoreguesser.feature.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tatar.presentation.feature.result.ResultsViewModel
import com.tatar.presentation.viewmodel.factory.withFactory
import com.tatar.scoreguesser.R
import com.tatar.scoreguesser.ScoreGuesserApplication
import com.tatar.scoreguesser.base.BaseFragment
import com.tatar.scoreguesser.databinding.FragmentResultsBinding

class ResultsFragment : BaseFragment(R.layout.fragment_results) {

    override val viewModel: ResultsViewModel by viewModels { withFactory(viewModelFactory) }

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
        observeEvents(binding)
        bindInteractions(binding)
    }

    private fun setUpView(binding: FragmentResultsBinding) {

    }

    private fun observeState(binding: FragmentResultsBinding) {

    }

    private fun observeEvents(binding: FragmentResultsBinding) {

    }

    private fun bindInteractions(binding: FragmentResultsBinding) {
        binding.restartTv.setOnClickListener { viewModel.onRestartClicked() }
    }
}
