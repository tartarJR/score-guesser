package com.tatar.data.source

import com.tatar.core.data.Result
import com.tatar.data.feature.prediction.model.Matches
import com.tatar.data.feature.prediction.model.MatchesError
import com.tatar.data.feature.result.model.MatchResults
import com.tatar.data.feature.result.model.MatchResultsError
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {
    fun getMatches(): Single<Result<Matches, MatchesError>>
    fun getMatchResults(): Single<Result<MatchResults, MatchResultsError>>
}
