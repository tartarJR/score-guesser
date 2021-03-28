package com.tatar.presentation.feature.main

import com.tatar.domain.feature.main.SetLastScreenUseCase
import com.tatar.presentation.viewmodel.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject internal constructor(
    private val setLastScreenUseCase: SetLastScreenUseCase
) : BaseViewModel() {
    fun onViewPaused(lastScreen: String?) {
        setLastScreenUseCase.invoke(SetLastScreenUseCase.Params(lastScreen))
            .subscribe { Timber.tag("SCORE-GUSSER").d("Last screen is saved!") }
            .addToDisposables()
    }
}
