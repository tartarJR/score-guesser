package com.tatar.data.dagger

import com.tatar.data.feature.prediction.repository.PredictionRepositoryImpl
import com.tatar.data.feature.prediction.repository.SettingsRepositoryImpl
import com.tatar.data.feature.result.repository.ResultRepositoryImpl
import com.tatar.domain.repository.PredictionRepository
import com.tatar.domain.repository.SettingsRepository
import com.tatar.domain.repository.ResultRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @Binds
    fun bindsPredictionRepository(predictionRepository: PredictionRepositoryImpl): PredictionRepository

    @Binds
    fun bindsResultRepository(resultRepository: ResultRepositoryImpl): ResultRepository

    @Binds
    fun bindsSettingsRepository(settingsRepository: SettingsRepositoryImpl): SettingsRepository
}
