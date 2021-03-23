package com.tatar.data.feature.result.mapper

import com.tatar.core.data.DataClassMapper
import com.tatar.core.data.ErrorResult
import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.data.feature.result.model.MatchResults
import com.tatar.data.feature.result.model.MatchResultsError
import com.tatar.domain.feature.result.entity.MatchResultEntity
import com.tatar.domain.feature.result.entity.MatchResultsEntity
import com.tatar.domain.feature.result.entity.MatchResultsErrorEntity

internal object MatchResultsMapper :
    DataClassMapper<MatchResults, MatchResultsError, MatchResultsEntity, MatchResultsErrorEntity> {

    override fun mapToNewModel(result: Result<MatchResults, MatchResultsError>): Result<MatchResultsEntity, MatchResultsErrorEntity> {
        return when (result) {
            is SuccessResult -> SuccessResult(
                MatchResultsEntity(
                    matchResults = result.data.matchResults.map {
                        MatchResultEntity(
                            homeTeamName = it.homeTeamName,
                            awayTeamName = it.awayTeamName,
                            homeTeamScore = it.homeTeamScore,
                            awayTeamScore = it.awayTeamScore

                        )
                    }
                )
            )
            is ErrorResult -> ErrorResult(MatchResultsErrorEntity)
        }
    }
}
