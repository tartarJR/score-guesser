package com.tatar.presentation.feature.result.mapper

import com.tatar.core.data.DataClassMapper
import com.tatar.core.data.ErrorResult
import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.domain.feature.result.entity.MatchResultsEntity
import com.tatar.domain.feature.result.entity.MatchResultsErrorEntity
import com.tatar.presentation.feature.result.model.MatchResultModel
import com.tatar.presentation.feature.result.model.MatchResultsErrorModel
import com.tatar.presentation.feature.result.model.MatchResultsModel

internal object MatchResultsEntityMapper :
    DataClassMapper<MatchResultsEntity, MatchResultsErrorEntity, MatchResultsModel, MatchResultsErrorModel> {

    override fun mapToNewModel(result: Result<MatchResultsEntity, MatchResultsErrorEntity>): Result<MatchResultsModel, MatchResultsErrorModel> {
        return when (result) {
            is SuccessResult -> SuccessResult(
                MatchResultsModel(
                    isEmpty = result.data.getFilteredMatchResults().isEmpty(),
                    matchResults = result.data.getFilteredMatchResults()
                        .map {
                            MatchResultModel(
                                matchIdentifier = it.getMatchIdentifier(),
                                homeTeamName = it.getNonNullHomeTeamName(),
                                awayTeamName = it.getNonNullAwayTeamName(),
                                homeTeamScore = it.getNonNullHomeTeamScore(),
                                awayTeamScore = it.getNonNullAwayTeamScore(),
                                homeTeamScorePrediction = it.getNonNullHomeTeamPrediction(),
                                awayTeamScorePrediction = it.getNonNullAwayTeamPrediction(),
                                hasPrediction = it.isPredictionDataAvailable()
                            )
                        }
                )
            )
            else -> ErrorResult(MatchResultsErrorModel)
        }
    }
}
