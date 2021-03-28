package com.tatar.presentation.feature.result

import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.core.data.toNewModel
import com.tatar.domain.base.invoke
import com.tatar.domain.feature.result.usecase.GetResultsUseCase
import com.tatar.domain.feature.result.usecase.ResetPredictionsUseCase
import com.tatar.presentation.feature.result.mapper.MatchResultsEntityMapper
import com.tatar.presentation.feature.result.model.MatchResultModel
import com.tatar.presentation.feature.result.model.MatchResultsErrorModel
import com.tatar.presentation.feature.result.model.MatchResultsModel
import com.tatar.presentation.resource.text.TextResource
import com.tatar.presentation.viewmodel.base.StatefulViewModel
import javax.inject.Inject

class ResultsViewModel @Inject constructor(
    private val getResultsUseCase: GetResultsUseCase,
    private val resetPredictionsUseCase: ResetPredictionsUseCase
) : StatefulViewModel<ResultsViewModel.State>(State()) {

    data class State(
        val isLoading: Boolean = false,
        val isContentVisible: Boolean = false,
        val isEmpty: Boolean = false,
        val isError: Boolean = false,
        val results: List<MatchResultModel> = emptyList(),
    )

    init {
        makeResultsCall()
    }

    fun makeResultsCall() {
        getResultsUseCase()
            .toNewModel(MatchResultsEntityMapper)
            .doOnSubscribe { onRequestStarted() }
            .doFinally { onRequestFinished() }
            .subscribe(
                { onResults(it) },
                { onResultsError(it) }
            )
            .addToDisposables()
    }

    private fun onResults(result: Result<MatchResultsModel, MatchResultsErrorModel>) {
        when (result) {
            is SuccessResult -> onResultsSuccess(result.data)
            else -> onResultsError()
        }
    }

    private fun onResultsSuccess(data: MatchResultsModel) {
        changeState {
            it.copy(
                isLoading = false,
                isContentVisible = !data.isEmpty,
                isError = false,
                isEmpty = data.isEmpty,
                results = data.matchResults,
            )
        }
    }

    private fun onResultsError(error: Throwable? = null) {
        error?.printStackTrace()

        changeState {
            it.copy(
                isLoading = false,
                isContentVisible = false,
                isError = true,
                isEmpty = false,
            )
        }
    }

    private fun onRequestStarted() {
        changeState {
            it.copy(
                isLoading = true,
                isContentVisible = false,
                isError = false,
                isEmpty = false,
            )
        }
    }

    private fun onRequestFinished() {
        changeState { it.copy(isLoading = false) }
    }

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
