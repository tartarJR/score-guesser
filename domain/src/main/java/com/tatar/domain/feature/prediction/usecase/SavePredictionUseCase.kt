package com.tatar.domain.feature.prediction.usecase

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.qualifier.MainThread
import com.tatar.domain.base.CompletableUseCase
import com.tatar.domain.feature.prediction.repository.PredictionRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class SavePredictionUseCase @Inject internal constructor(
    @MainThread mainScheduler: Scheduler,
    @BackgroundThread backgroundScheduler: Scheduler,
    private val predictionRepository: PredictionRepository,
) : CompletableUseCase<SavePredictionUseCase.Params>(
    mainScheduler, backgroundScheduler
) {
    data class Params(
        val matchIdentifier: String,
        val homeTeamScore: String,
        val awayTeamScore: String
    )

    override fun execute(params: Params): Completable {
        return predictionRepository.getMatchLocally(params.matchIdentifier)
            .flatMapCompletable {
                predictionRepository.saveMatchLocally(
                    it.copy(
                        homeScorePrediction = params.homeTeamScore,
                        awayScorePrediction = params.awayTeamScore
                    )
                )
            }
    }
}
