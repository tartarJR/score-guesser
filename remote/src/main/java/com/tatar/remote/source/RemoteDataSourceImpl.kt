package com.tatar.remote.source

import com.tatar.core.dagger.scope.ApplicationScope
import com.tatar.core.data.Result
import com.tatar.data.feature.prediction.model.Matches
import com.tatar.data.feature.prediction.model.MatchesError
import com.tatar.data.feature.result.model.MatchResults
import com.tatar.data.feature.result.model.MatchResultsError
import com.tatar.data.source.RemoteDataSource
import com.tatar.remote.api.ScoreGuesserAPI
import com.tatar.remote.source.prediction.mapper.MatchesResponseMapper
import com.tatar.remote.source.prediction.model.MatchesFailureResponse
import com.tatar.remote.source.prediction.model.MatchesResponse
import com.tatar.remote.source.result.mapper.MatchResultsResponseMapper
import com.tatar.remote.source.result.model.MatchResultsFailureResponse
import com.tatar.remote.source.result.model.MatchResultsResponse
import com.tatar.remote.util.mapError
import com.tatar.remote.util.toDataResult
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@ApplicationScope
class RemoteDataSourceImpl @Inject constructor(
    private val scoreGuesserAPI: ScoreGuesserAPI
) : RemoteDataSource {

    override fun getMatches(): Single<Result<Matches, MatchesError>> {
        return scoreGuesserAPI.getMatches()
            .mapError<MatchesFailureResponse, MatchesResponse>()
            .toDataResult(MatchesResponseMapper)
    }

    override fun getMatchResults(): Single<Result<MatchResults, MatchResultsError>> {
        return scoreGuesserAPI.getResults()
            .mapError<MatchResultsFailureResponse, MatchResultsResponse>()
            .toDataResult(MatchResultsResponseMapper)
    }
}
