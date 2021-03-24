package com.tatar.local.room.feature.prediction.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "predictions_table")
data class PredictionRoomEntity(
    @PrimaryKey(autoGenerate = false)
    val matchIdentifier: String, // // I would never do that in a real-life situation, but it works in this case. I did it for the sake of simplicity since I did not have much time.
    val homeTeamName: String,
    val awayTeamName: String,
    val homeScorePrediction: String,
    val awayScorePrediction: String
)
