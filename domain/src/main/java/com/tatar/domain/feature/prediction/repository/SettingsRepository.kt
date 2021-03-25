package com.tatar.domain.feature.prediction.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface SettingsRepository {
    fun getLastRequestTime(): Single<Long>
    fun getLastScreen(): Single<String>
    fun setLastRequestTime(lastRequestTime: Long): Completable
    fun setLastScreen(lastScreen: String): Completable
}
