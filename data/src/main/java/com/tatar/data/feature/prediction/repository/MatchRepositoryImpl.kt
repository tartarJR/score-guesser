package com.tatar.data.feature.prediction.repository

import com.tatar.core.dagger.scope.ApplicationScope
import com.tatar.core.data.Result
import com.tatar.core.data.toNewModel
import com.tatar.data.feature.prediction.mapper.MatchesMapper
import com.tatar.data.source.LocalDataSource
import com.tatar.data.source.RemoteDataSource
import com.tatar.domain.feature.prediction.entity.MatchesEntity
import com.tatar.domain.feature.prediction.entity.MatchesErrorEntity
import com.tatar.domain.feature.prediction.repository.MatchRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@ApplicationScope
class MatchRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MatchRepository {

    override fun getMatches(): Single<Result<MatchesEntity, MatchesErrorEntity>> {
        return remoteDataSource.getMatches()
            .toNewModel(MatchesMapper)
    }
}
