package com.tatar.domain.feature.prediction.usecase

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.qualifier.MainThread
import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.domain.base.SingleUseCase
import com.tatar.domain.feature.prediction.entity.MatchesEntity
import com.tatar.domain.feature.prediction.entity.MatchesErrorEntity
import com.tatar.domain.feature.prediction.repository.PredictionRepository
import com.tatar.domain.feature.prediction.repository.SettingsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetMatchesUseCase @Inject internal constructor(
    @MainThread mainScheduler: Scheduler,
    @BackgroundThread backgroundScheduler: Scheduler,
    private val settingsRepository: SettingsRepository,
    private val predictionRepository: PredictionRepository,
) : SingleUseCase<Unit, Result<MatchesEntity, MatchesErrorEntity>>(
    mainScheduler, backgroundScheduler
) {
    override fun execute(params: Unit): Single<Result<MatchesEntity, MatchesErrorEntity>> {
        return settingsRepository.getLastRequestTime()
            .flatMap { onLastRequestTimeResult(it) }
            .onErrorResumeNext { getPredictionsRemotely() }
    }

    private fun onLastRequestTimeResult(lastRequestTimeResult: Long): Single<Result<MatchesEntity, MatchesErrorEntity>> {
        return if (hasMoreThanAMinutePassedSinceLastRequest(lastRequestTimeResult)) {
            getPredictionsRemotely()
        } else {
            getPredictionsLocally()
        }
    }

    private fun getPredictionsRemotely(): Single<Result<MatchesEntity, MatchesErrorEntity>> {
        return predictionRepository.getMatches()
            .flatMap { onMatchesResult(it) }
    }

    private fun onMatchesResult(it: Result<MatchesEntity, MatchesErrorEntity>): Single<Result<MatchesEntity, MatchesErrorEntity>> {
        return onPredictionsResult(it).andThen(Single.just(it))
    }

    private fun onPredictionsResult(result: Result<MatchesEntity, MatchesErrorEntity>): Completable {
        return if (result is SuccessResult) {
            val filteredMatches = result.data
                .matches
                .filter { it.isGameDataAvailable() }

            Completable.merge(
                listOf(
                    settingsRepository.setLastRequestTime(System.currentTimeMillis()),
                    predictionRepository.saveMatchesLocally(filteredMatches)
                )
            )
        } else {
            Completable.complete()
        }
    }

    private fun getPredictionsLocally(): Single<Result<MatchesEntity, MatchesErrorEntity>> {
        return predictionRepository.getMatchesLocally()
            .map { SuccessResult(MatchesEntity(it)) }
    }

    private fun hasMoreThanAMinutePassedSinceLastRequest(lastRequestTimeResult: Long): Boolean {
        return System.currentTimeMillis() - lastRequestTimeResult > MILLIS_OF_A_MINUTE
    }

    companion object {
        private const val MILLIS_OF_A_MINUTE = 60 * 1000
    }
}
