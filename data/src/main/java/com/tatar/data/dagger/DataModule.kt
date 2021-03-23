package com.tatar.data.dagger

import com.tatar.data.feature.prediction.repository.MatchRepositoryImpl
import com.tatar.data.feature.result.repository.ResultRepositoryImpl
import com.tatar.domain.feature.prediction.repository.MatchRepository
import com.tatar.domain.feature.result.repository.ResultRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @Binds
    fun bindsMatchRepository(matchRepository: MatchRepositoryImpl): MatchRepository

    @Binds
    fun bindsResultRepository(resultRepository: ResultRepositoryImpl): ResultRepository
}
