package com.tatar.data.feature.prediction.repository

import com.tatar.core.dagger.scope.ApplicationScope
import com.tatar.core.data.Result
import com.tatar.core.data.toNewModel
import com.tatar.data.feature.prediction.mapper.MatchesMapper
import com.tatar.data.feature.prediction.mapper.MatchesMapper.toMatch
import com.tatar.data.feature.prediction.mapper.MatchesMapper.toMatchEntity
import com.tatar.data.source.LocalDataSource
import com.tatar.data.source.RemoteDataSource
import com.tatar.domain.feature.prediction.entity.MatchEntity
import com.tatar.domain.feature.prediction.entity.MatchesEntity
import com.tatar.domain.feature.prediction.entity.MatchesErrorEntity
import com.tatar.domain.repository.PredictionRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@ApplicationScope
class PredictionRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : PredictionRepository {

    override fun getMatches(): Single<Result<MatchesEntity, MatchesErrorEntity>> {
        return remoteDataSource.getMatches()
            .toNewModel(MatchesMapper)
    }

    override fun getMatchLocally(matchIdentifier: String): Single<MatchEntity> {
        return localDataSource.getPrediction(matchIdentifier)
            .map { toMatchEntity(it) }
    }

    override fun getMatchesLocally(): Single<List<MatchEntity>> {
        return localDataSource.getPredictions()
            .map { it.map { match -> toMatchEntity(match) } }
    }

    override fun saveMatchesLocally(matches: List<MatchEntity>): Completable {
        return localDataSource.insertPredictions(matches.map { toMatch(it) })
    }

    override fun saveMatchLocally(match: MatchEntity): Completable {
        return localDataSource.insertPrediction(toMatch(match))
    }
}
