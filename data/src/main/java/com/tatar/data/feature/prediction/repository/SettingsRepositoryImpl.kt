package com.tatar.data.feature.prediction.repository

import com.tatar.core.dagger.scope.ApplicationScope
import com.tatar.data.source.LocalDataSource
import com.tatar.domain.feature.prediction.repository.SettingsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@ApplicationScope
class SettingsRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : SettingsRepository {

    override fun getLastRequestTime(): Single<Long> {
        return localDataSource.getLastRequestTime()
    }

    override fun getLastScreen(): Single<String> {
        return localDataSource.getLastScreen()
    }

    override fun setLastRequestTime(lastRequestTime: Long): Completable {
        return localDataSource.setLastRequestTime(lastRequestTime)
    }

    override fun setLastScreen(lastScreen: String): Completable {
        return localDataSource.setLastScreen(lastScreen)
    }
}
