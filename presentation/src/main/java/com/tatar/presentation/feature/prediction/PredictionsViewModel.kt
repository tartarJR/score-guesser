package com.tatar.presentation.feature.prediction

import com.tatar.domain.feature.prediction.usecase.GetMatchesUseCase
import com.tatar.presentation.resource.navigation.PredictionsToResults
import com.tatar.presentation.viewmodel.base.StatefulViewModel
import javax.inject.Inject

class PredictionsViewModel @Inject internal constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
) : StatefulViewModel<PredictionsViewModel.State>(State()) {

    data class State(
        val isLoading: Boolean = false
    )

    fun onResultsClicked() {
        // TODO add predictions param
        navigateTo(PredictionsToResults)
    }
}
