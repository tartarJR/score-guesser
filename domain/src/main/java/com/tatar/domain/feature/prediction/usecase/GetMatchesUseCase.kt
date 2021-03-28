package com.tatar.domain.feature.prediction.usecase

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.qualifier.MainThread
import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.domain.base.SingleUseCase
import com.tatar.domain.feature.prediction.entity.MatchEntity
import com.tatar.domain.feature.prediction.entity.MatchesEntity
import com.tatar.domain.feature.prediction.entity.MatchesErrorEntity
import com.tatar.domain.repository.PredictionRepository
import com.tatar.domain.repository.SettingsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.Singles
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
        return Singles.zip(
            predictionRepository.getMatches(),
            predictionRepository.getMatchesLocally()
        ).flatMap { onPredictionsResult(it.first, it.second) }
    }

    private fun onPredictionsResult(
        remoteResult: Result<MatchesEntity, MatchesErrorEntity>,
        localResult: List<MatchEntity>
    ): Single<Result<MatchesEntity, MatchesErrorEntity>> {
        return if (remoteResult is SuccessResult) {
            val combinedEntity = MatchesEntity(getCombinedMatches(remoteResult, localResult))
            val combinedResult = remoteResult.copy(data = combinedEntity)

            onResultCombined(combinedEntity).andThen(Single.just(combinedResult))
        } else {
            Single.just(remoteResult)
        }
    }

    private fun getCombinedMatches(
        remoteResult: SuccessResult<MatchesEntity, MatchesErrorEntity>,
        localResult: List<MatchEntity>
    ): List<MatchEntity> {
        val filteredMatches = remoteResult.data
            .matches
            .filter { it.isGameDataAvailable() }

        return filteredMatches.map { remote ->
            val local = localResult.find { local -> local.getMatchIdentifier() == remote.getMatchIdentifier() }

            if (remote.getMatchIdentifier() == local?.getMatchIdentifier()) local
            else remote
        }
    }

    private fun onResultCombined(combinedEntity: MatchesEntity): Completable {
        return Completable.merge(
            listOf(
                settingsRepository.setLastRequestTime(System.currentTimeMillis()),
                predictionRepository.saveMatchesLocally(combinedEntity.matches)
            )
        )
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
