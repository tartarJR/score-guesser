package com.tatar.domain.feature.prediction.repository

import com.tatar.core.data.Result
import com.tatar.domain.feature.prediction.entity.MatchEntity
import com.tatar.domain.feature.prediction.entity.MatchesEntity
import com.tatar.domain.feature.prediction.entity.MatchesErrorEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface PredictionRepository {
    fun getMatches(): Single<Result<MatchesEntity, MatchesErrorEntity>>
    fun getMatchLocally(matchIdentifier: String): Single<MatchEntity>
    fun getMatchesLocally(): Single<List<MatchEntity>>
    fun saveMatchesLocally(matches: List<MatchEntity>): Completable
    fun saveMatchLocally(match: MatchEntity): Completable
}
