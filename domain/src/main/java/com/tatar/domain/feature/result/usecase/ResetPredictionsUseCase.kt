package com.tatar.domain.feature.result.usecase

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.qualifier.MainThread
import com.tatar.domain.base.CompletableUseCase
import com.tatar.domain.repository.PredictionRepository
import com.tatar.domain.repository.SettingsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class ResetPredictionsUseCase @Inject internal constructor(
    @MainThread mainScheduler: Scheduler,
    @BackgroundThread backgroundScheduler: Scheduler,
    private val settingsRepository: SettingsRepository,
    private val predictionRepository: PredictionRepository
) : CompletableUseCase<Unit>(
    mainScheduler, backgroundScheduler
) {
    override fun execute(params: Unit): Completable {
        return Completable.fromAction {
            settingsRepository.clearSettings()
            predictionRepository.clearPredictions()
        }
    }
}
