package com.tatar.remote.source.result.mapper

import com.tatar.core.data.ErrorResult
import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.data.feature.result.model.MatchResult
import com.tatar.data.feature.result.model.MatchResults
import com.tatar.data.feature.result.model.MatchResultsError
import com.tatar.remote.source.result.model.MatchResultsFailureResponse
import com.tatar.remote.source.result.model.MatchResultsResponse
import com.tatar.remote.source.result.model.MatchResultsSuccessResponse
import com.tatar.remote.util.ResponseMapper

object MatchResultsResponseMapper :
    ResponseMapper<MatchResultsResponse, MatchResultsSuccessResponse, MatchResultsFailureResponse, MatchResults, MatchResultsError> {

    override fun mapToDataModel(result: MatchResultsResponse): Result<MatchResults, MatchResultsError> {
        return when (result) {
            is MatchResultsFailureResponse -> ErrorResult(MatchResultsError)
            is MatchResultsSuccessResponse -> SuccessResult(
                MatchResults(
                    matchResults = result.matchResults.map { match ->
                        MatchResult(
                            homeTeamName = match.homeTeamName,
                            awayTeamName = match.awayTeamName,
                            homeTeamScore = match.homeTeamScore,
                            awayTeamScore = match.awayTeamScore
                        )
                    }
                )
            )
        }
    }
}
