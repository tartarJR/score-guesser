package com.tatar.data.feature.result.repository

import com.tatar.core.dagger.scope.ApplicationScope
import com.tatar.core.data.Result
import com.tatar.core.data.toNewModel
import com.tatar.data.feature.result.mapper.MatchResultsMapper
import com.tatar.data.source.LocalDataSource
import com.tatar.data.source.RemoteDataSource
import com.tatar.domain.feature.result.entity.MatchResultsEntity
import com.tatar.domain.feature.result.entity.MatchResultsErrorEntity
import com.tatar.domain.feature.result.repository.ResultRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@ApplicationScope
class ResultRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : ResultRepository {

    override fun getMatchResults(): Single<Result<MatchResultsEntity, MatchResultsErrorEntity>> {
        return remoteDataSource.getMatchResults()
            .toNewModel(MatchResultsMapper)
    }
}
