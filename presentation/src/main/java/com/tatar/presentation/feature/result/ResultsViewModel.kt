package com.tatar.presentation.feature.result

import com.tatar.domain.base.invoke
import com.tatar.domain.feature.result.usecase.GetResultsUseCase
import com.tatar.domain.feature.result.usecase.ResetPredictionsUseCase
import com.tatar.presentation.resource.text.TextResource
import com.tatar.presentation.viewmodel.base.StatefulViewModel
import javax.inject.Inject

class ResultsViewModel @Inject constructor(
    private val getResultsUseCase: GetResultsUseCase,
    private val resetPredictionsUseCase: ResetPredictionsUseCase
) : StatefulViewModel<ResultsViewModel.State>(State()) {

    data class State(
        val isLoading: Boolean = false,
    )

    fun onRestartClicked() {
        resetPredictionsUseCase.invoke()
            .subscribe(
                { onResetPredictionsResult() },
                { onResetPredictionError() }
            )
            .addToDisposables()
    }

    private fun onResetPredictionsResult() {
        navigateBack()
    }

    private fun onResetPredictionError() {
        showError(TextResource.RESET_DATA_ERROR)
    }
}
