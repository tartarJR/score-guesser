package com.tatar.domain.feature.result.repository

import com.tatar.core.data.Result
import com.tatar.domain.feature.result.entity.MatchResultsEntity
import com.tatar.domain.feature.result.entity.MatchResultsErrorEntity
import io.reactivex.rxjava3.core.Single

interface ResultRepository {
    fun getMatchResults(): Single<Result<MatchResultsEntity, MatchResultsErrorEntity>>
}
