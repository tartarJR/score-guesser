package com.tatar.presentation.feature.prediction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.core.data.toNewModel
import com.tatar.domain.base.invoke
import com.tatar.domain.feature.prediction.usecase.GetMatchesUseCase
import com.tatar.domain.feature.prediction.usecase.SavePredictionUseCase
import com.tatar.presentation.feature.prediction.mapper.MatchesEntityMapper
import com.tatar.presentation.feature.prediction.model.MatchModel
import com.tatar.presentation.feature.prediction.model.MatchesErrorModel
import com.tatar.presentation.feature.prediction.model.MatchesModel
import com.tatar.presentation.resource.navigation.PredictionsToResults
import com.tatar.presentation.resource.text.TextResource
import com.tatar.presentation.util.Event
import com.tatar.presentation.viewmodel.base.StatefulViewModel
import javax.inject.Inject

class PredictionsViewModel @Inject internal constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val savePredictionUseCase: SavePredictionUseCase
) : StatefulViewModel<PredictionsViewModel.State>(State()) {

    data class State(
        val isLoading: Boolean = false,
        val isContentVisible: Boolean = false,
        val isEmpty: Boolean = false,
        val isError: Boolean = false,
        val matches: List<MatchModel> = emptyList(),
        val isResultsButtonVisible: Boolean = false
    )

    private val _showPredictionDialog = MutableLiveData<Event<MatchModel>>()
    val showPredictionDialog: LiveData<Event<MatchModel>> get() = _showPredictionDialog

    init {
        makePredictionsCall()
    }

    private fun makePredictionsCall() {
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
                matches = data.matches,
                isResultsButtonVisible = data.isResultsButtonVisible
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
                isEmpty = false,
                isResultsButtonVisible = false
            )
        }
    }

    fun onResultsClicked() {
        // TODO add predictions param
        navigateTo(PredictionsToResults)
    }

    fun onMatchesItemClick(match: MatchModel) {
        _showPredictionDialog.postValue(Event(match))
    }

    fun onPredictionsApplied(
        matchIdentifier: String,
        homeTeamScore: String,
        awayTeamScore: String
    ) {
        savePredictionUseCase.invoke(
            SavePredictionUseCase.Params(
                matchIdentifier,
                homeTeamScore,
                awayTeamScore
            )
        )
            .doOnSubscribe { onRequestStarted() }
            .subscribe(
                { onPredictionResult() },
                { onPredictionError() }
            )
            .addToDisposables()
    }

    private fun onPredictionResult() {
        showToast(TextResource.SAVE_PREDICTION_SUCCESS)
        makePredictionsCall()
    }

    private fun onPredictionError() {
        showError(TextResource.SAVE_PREDICTION_ERROR)
    }

    private fun onRequestStarted() {
        changeState {
            it.copy(
                isLoading = true,
                isContentVisible = false,
                isError = false,
                isEmpty = false,
                isResultsButtonVisible = false
            )
        }
    }

    private fun onRequestFinished() {
        changeState { it.copy(isLoading = false) }
    }
}
