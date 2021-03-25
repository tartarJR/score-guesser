package com.tatar.local.room.feature.prediction.mapper

import com.tatar.data.feature.prediction.model.Match
import com.tatar.local.room.feature.prediction.entity.PredictionRoomEntity
import com.tatar.local.util.RoomEntityMapper

internal object PredictionRoomEntityMapper : RoomEntityMapper<Match, PredictionRoomEntity> {
    override fun mapToData(roomEntity: PredictionRoomEntity): Match {
        return Match(
            matchIdentifier = roomEntity.matchIdentifier,
            homeTeamName = roomEntity.homeTeamName,
            awayTeamName = roomEntity.awayTeamName,
            homeScorePrediction = roomEntity.homeScorePrediction,
            awayScorePrediction = roomEntity.awayScorePrediction
        )
    }

    override fun mapFromData(data: Match): PredictionRoomEntity {
        return PredictionRoomEntity(
            matchIdentifier = data.matchIdentifier,
            homeTeamName = data.homeTeamName ?: "",
            awayTeamName = data.awayTeamName ?: "",
            homeScorePrediction = data.homeScorePrediction,
            awayScorePrediction = data.awayScorePrediction
        )
    }
}
