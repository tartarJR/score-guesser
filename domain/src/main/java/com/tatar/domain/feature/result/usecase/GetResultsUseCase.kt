package com.tatar.domain.feature.result.usecase

import com.tatar.core.dagger.qualifier.BackgroundThread
import com.tatar.core.dagger.qualifier.MainThread
import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.domain.base.SingleUseCase
import com.tatar.domain.feature.prediction.entity.MatchEntity
import com.tatar.domain.feature.result.entity.MatchResultEntity
import com.tatar.domain.feature.result.entity.MatchResultsEntity
import com.tatar.domain.feature.result.entity.MatchResultsErrorEntity
import com.tatar.domain.repository.PredictionRepository
import com.tatar.domain.repository.ResultRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.Singles
import javax.inject.Inject

class GetResultsUseCase @Inject internal constructor(
    @MainThread mainScheduler: Scheduler,
    @BackgroundThread backgroundScheduler: Scheduler,
    private val resultRepository: ResultRepository,
    private val predictionRepository: PredictionRepository
) : SingleUseCase<Unit, Result<MatchResultsEntity, MatchResultsErrorEntity>>(
    mainScheduler, backgroundScheduler
) {
    override fun execute(params: Unit): Single<Result<MatchResultsEntity, MatchResultsErrorEntity>> {
        return Singles.zip(
            resultRepository.getMatchResults(),
            predictionRepository.getMatchesLocally()
        ).flatMap { onCombinedResults(it.first, it.second) }
    }

    private fun onCombinedResults(
        remoteResult: Result<MatchResultsEntity, MatchResultsErrorEntity>?,
        localResult: List<MatchEntity>
    ) = if (remoteResult is SuccessResult) {
        val filteredRemoteResults = remoteResult.data
            .getFilteredMatchResults()
            .filter { it.isResultDataAvailable() }

        val combinedResults = getCombinedResults(filteredRemoteResults, localResult)

        Single.just(remoteResult.copy(data = MatchResultsEntity(combinedResults)))
    } else {
        Single.just(remoteResult)
    }

    private fun getCombinedResults(
        filteredRemoteResults: List<MatchResultEntity>,
        localResult: List<MatchEntity>
    ): List<MatchResultEntity> {
        return filteredRemoteResults.map { remote ->
            val local =
                localResult.find { local -> local.getMatchIdentifier() == remote.getMatchIdentifier() }

            if (remote.getMatchIdentifier() == local?.getMatchIdentifier()) {
                remote.copy(
                    homeTeamPrediction = local.homeScorePrediction,
                    awayTeamPrediction = local.awayScorePrediction
                )
            } else {
                remote
            }
        }
    }
}
