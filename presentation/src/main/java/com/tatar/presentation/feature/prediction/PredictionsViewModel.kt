package com.tatar.presentation.feature.prediction

import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.core.data.toNewModel
import com.tatar.domain.base.invoke
import com.tatar.domain.feature.prediction.usecase.GetMatchesUseCase
import com.tatar.presentation.feature.prediction.mapper.MatchesEntityMapper
import com.tatar.presentation.feature.prediction.model.MatchModel
import com.tatar.presentation.feature.prediction.model.MatchesErrorModel
import com.tatar.presentation.feature.prediction.model.MatchesModel
import com.tatar.presentation.resource.navigation.PredictionsToResults
import com.tatar.presentation.viewmodel.base.StatefulViewModel
import javax.inject.Inject

class PredictionsViewModel @Inject internal constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
) : StatefulViewModel<PredictionsViewModel.State>(State()) {

    data class State(
        val isLoading: Boolean = false,
        val isContentVisible: Boolean = false,
        val isEmpty: Boolean = false,
        val isError: Boolean = false,
        val matches: List<MatchModel> = emptyList()
    )

    init {
        makePromotionsCall()
    }

    private fun makePromotionsCall() {
        getMatchesUseCase()
            .toNewModel(MatchesEntityMapper)
            .doOnSubscribe { onRequestStarted() }
            .doFinally { onRequestFinished() }
            .subscribe(
                { onMatchesResult(it) },
                { onMatchesError(it) }
            )
            .addToDisposables()
    }

    private fun onMatchesResult(result: Result<MatchesModel, MatchesErrorModel>) {
        when (result) {
            is SuccessResult -> onMatchesSuccess(result.data)
            else -> onMatchesError()
        }
    }

    private fun onMatchesSuccess(data: MatchesModel) {
        changeState {
            it.copy(
                isLoading = false,
                isContentVisible = !data.isEmpty,
                isError = false,
                isEmpty = data.isEmpty,
                matches = data.matches
            )
        }
    }

    private fun onMatchesError(error: Throwable? = null) {
        error?.printStackTrace()

        changeState {
            it.copy(
                isLoading = false,
                isContentVisible = false,
                isError = true,
                isEmpty = false
            )
        }
    }

    private fun onRequestStarted() {
        changeState {
            it.copy(
                isLoading = true,
                isContentVisible = false,
                isError = false,
                isEmpty = false
            )
        }
    }

    private fun onRequestFinished() {
        changeState { it.copy(isLoading = false) }
    }

    fun onResultsClicked() {
        // TODO add predictions param
        navigateTo(PredictionsToResults)
    }

    fun onMatchesItemClick(match: MatchModel) {
        // TODO implement item click
    }
}
