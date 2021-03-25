package com.tatar.data.source

import com.tatar.data.feature.prediction.model.Match
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {
    fun setLastScreen(lastScreen: String): Completable
    fun setLastRequestTime(lastRequestTime: Long): Completable
    fun getLastScreen(): Single<String>
    fun getLastRequestTime(): Single<Long>
    fun clearPrefs()
    fun getPredictions(): Single<List<Match>>
    fun insertPrediction(prediction: Match): Completable
    fun insertPredictions(predictions: List<Match>): Completable
    fun clearDB()
}
