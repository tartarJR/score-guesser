package com.tatar.domain.feature.main

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.qualifier.MainThread
import com.tatar.domain.base.CompletableUseCase
import com.tatar.domain.feature.main.entity.LastScreenEntity.Companion.SCREEN_PREDICTIONS
import com.tatar.domain.repository.SettingsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class SetLastScreenUseCase @Inject internal constructor(
    @MainThread mainScheduler: Scheduler,
    @BackgroundThread backgroundScheduler: Scheduler,
    private val settingsRepository: SettingsRepository,
) : CompletableUseCase<SetLastScreenUseCase.Params>(
    mainScheduler, backgroundScheduler
) {
    data class Params(
        val lastScreen: String?
    )

    override fun execute(params: Params): Completable {
        val lastScreen = params.lastScreen ?: SCREEN_PREDICTIONS
        return settingsRepository.setLastScreen(lastScreen)
    }
}
