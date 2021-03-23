package com.tatar.remote.source.prediction.model

import com.squareup.moshi.Json

sealed class MatchesResponse

data class MatchesSuccessResponse(
    val matches: List<MatchResponse>
) : MatchesResponse()

data class MatchResponse(
    @Json(name = "team1") val homeTeamName: String?,
    @Json(name = "team2") val awayTeamName: String?
)

object MatchesFailureResponse : MatchesResponse()
