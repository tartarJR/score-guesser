package com.tatar.presentation.feature.prediction.mapper

import com.tatar.core.data.DataClassMapper
import com.tatar.core.data.ErrorResult
import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.domain.feature.prediction.entity.MatchesEntity
import com.tatar.domain.feature.prediction.entity.MatchesErrorEntity
import com.tatar.presentation.feature.prediction.model.MatchModel
import com.tatar.presentation.feature.prediction.model.MatchesErrorModel
import com.tatar.presentation.feature.prediction.model.MatchesModel

internal object MatchesEntityMapper :
    DataClassMapper<MatchesEntity, MatchesErrorEntity, MatchesModel, MatchesErrorModel> {

    override fun mapToNewModel(result: Result<MatchesEntity, MatchesErrorEntity>): Result<MatchesModel, MatchesErrorModel> {
        return when (result) {
            is SuccessResult -> mapToSuccessModel(result)
            else -> ErrorResult(MatchesErrorModel)
        }
    }

    private fun mapToSuccessModel(result: SuccessResult<MatchesEntity, MatchesErrorEntity>): SuccessResult<MatchesModel, MatchesErrorModel> {
        val filteredMatches = result.data.matches.filter { it.isGameDataAvailable() }

        return SuccessResult(
            MatchesModel(
                isEmpty = filteredMatches.isEmpty(),
                matches = filteredMatches.map {
                    MatchModel(
                        matchIdentifier = it.getMatchIdentifier(),
                        homeTeamName = it.getNonNullHomeTeamName(),
                        awayTeamName = it.getNonNullAwayTeamName(),
                        homeTeamScore = it.homeScorePrediction,
                        awayTeamScore = it.awayScorePrediction
                    )
                }
            )
        )
    }
}
