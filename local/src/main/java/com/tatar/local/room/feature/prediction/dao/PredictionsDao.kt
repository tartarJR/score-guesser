package com.tatar.local.room.feature.prediction.dao

import androidx.room.Dao
import androidx.room.Query
import com.tatar.local.room.base.BaseDao
import com.tatar.local.room.feature.prediction.entity.PredictionRoomEntity
import io.reactivex.rxjava3.core.Single

@Dao
interface PredictionsDao : BaseDao<PredictionRoomEntity> {
    @Query("SELECT * FROM predictions_table")
    fun getPredictions(): Single<List<PredictionRoomEntity>>

    @Query("SELECT * FROM predictions_table WHERE matchIdentifier = :matchIdentifier")
    fun getPrediction(matchIdentifier: String): Single<PredictionRoomEntity>
}
