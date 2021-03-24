package com.tatar.local

import com.tatar.core.dagger.scope.ApplicationScope
import com.tatar.data.feature.prediction.model.Match
import com.tatar.data.source.LocalDataSource
import com.tatar.local.prefs.ScoreGuesserPrefs
import com.tatar.local.room.ScoreGuesserDB
import com.tatar.local.room.feature.prediction.dao.PredictionsDao
import com.tatar.local.room.feature.prediction.mapper.PredictionRoomEntityMapper
import com.tatar.local.util.fromData
import com.tatar.local.util.toDataList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import javax.inject.Inject

@ApplicationScope
class LocalDataSourceImpl @Inject constructor(
    private val db: ScoreGuesserDB,
    private val predictionsDao: PredictionsDao,
    private val scoreGuesserPrefs: ScoreGuesserPrefs
) : LocalDataSource {

    override fun setLastScreen(lastScreen: String): Completable {
        return scoreGuesserPrefs.setLastScreen(lastScreen)
    }

    override fun setLastRequestTime(lastRequestTime: Long): Completable {
        return scoreGuesserPrefs.setLastRequestTime(lastRequestTime)
    }

    override fun getLastScreen(): Maybe<String> {
        return scoreGuesserPrefs.getLastScreen()
    }

    override fun getLastRequestTime(): Maybe<Long> {
        return scoreGuesserPrefs.getLastRequestTime()
    }

    override fun clearPrefs() {
        scoreGuesserPrefs.clearPrefs()
    }

    override fun getPredictions(): Flowable<List<Match>> {
        return predictionsDao.getPredictions()
            .toDataList(PredictionRoomEntityMapper)
    }

    override fun insertPrediction(prediction: Match): Completable {
        return predictionsDao.insert(prediction.fromData(PredictionRoomEntityMapper))
    }

    override fun clearDB() {
        return db.clearAllTables()
    }
}
