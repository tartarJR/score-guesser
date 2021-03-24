package com.tatar.data.source

import com.tatar.data.feature.prediction.model.Match
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

interface LocalDataSource {
    fun setLastScreen(lastScreen: String): Completable
    fun setLastRequestTime(lastRequestTime: Long): Completable
    fun getLastScreen(): Maybe<String>
    fun getLastRequestTime(): Maybe<Long>
    fun clearPrefs()
    fun getPredictions(): Flowable<List<Match>>
    fun insertPrediction(prediction: Match): Completable
    fun clearDB()
}
