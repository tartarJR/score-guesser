package com.tatar.domain.feature.prediction.repository

import com.tatar.core.data.Result
import com.tatar.domain.feature.prediction.entity.MatchesEntity
import com.tatar.domain.feature.prediction.entity.MatchesErrorEntity
import io.reactivex.rxjava3.core.Single

interface PredictionRepository {
    fun getMatches(): Single<Result<MatchesEntity, MatchesErrorEntity>>
}
