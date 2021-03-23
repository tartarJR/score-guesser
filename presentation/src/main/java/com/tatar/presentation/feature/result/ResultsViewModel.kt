package com.tatar.presentation.feature.result

import androidx.lifecycle.SavedStateHandle
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.tatar.domain.feature.result.usecase.GetResultsUseCase
import com.tatar.presentation.viewmodel.base.StatefulViewModel
import com.tatar.presentation.viewmodel.factory.ViewModelAssistedFactory

class ResultsViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    private val getResultsUseCase: GetResultsUseCase,
) : StatefulViewModel<ResultsViewModel.State>(State()) {

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<ResultsViewModel> {
        override fun create(handle: SavedStateHandle): ResultsViewModel
    }

    data class State(
        val isLoading: Boolean = false,
    )

    fun onRestartClicked() {
        // TODO reset local data/cache
        navigateBack()
    }

    companion object {
        const val ARGUMENT_PREDICTIONS = "ARGUMENT_PREDICTIONS"
    }
}
