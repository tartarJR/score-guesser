package com.tatar.domain.feature.prediction.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

interface SettingsRepository {
    fun getLastRequestTime(): Maybe<Long>
    fun getLastScreen(): Maybe<String>
    fun setLastRequestTime(lastRequestTime: Long): Completable
    fun setLastScreen(lastScreen: String): Completable
}
