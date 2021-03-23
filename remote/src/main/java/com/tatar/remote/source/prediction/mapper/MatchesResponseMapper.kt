package com.tatar.remote.source.prediction.mapper

import com.tatar.core.data.ErrorResult
import com.tatar.core.data.Result
import com.tatar.core.data.SuccessResult
import com.tatar.data.feature.prediction.model.Match
import com.tatar.data.feature.prediction.model.Matches
import com.tatar.data.feature.prediction.model.MatchesError
import com.tatar.remote.source.prediction.model.MatchesFailureResponse
import com.tatar.remote.source.prediction.model.MatchesResponse
import com.tatar.remote.source.prediction.model.MatchesSuccessResponse
import com.tatar.remote.util.ResponseMapper

object MatchesResponseMapper :
    ResponseMapper<MatchesResponse, MatchesSuccessResponse, MatchesFailureResponse, Matches, MatchesError> {

    override fun mapToDataModel(result: MatchesResponse): Result<Matches, MatchesError> {
        return when (result) {
            is MatchesFailureResponse -> ErrorResult(MatchesError)
            is MatchesSuccessResponse -> SuccessResult(
                Matches(
                    matches = result.matches.map { match ->
                        Match(
                            homeTeamName = match.homeTeamName,
                            awayTeamName = match.awayTeamName
                        )
                    }
                )
            )
        }
    }
}
