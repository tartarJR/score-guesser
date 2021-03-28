package com.tatar.remote.source.result.model

import com.squareup.moshi.Json

sealed class MatchResultsResponse

data class MatchResultsSuccessResponse(
    @Json(name = "matches") val matchResults: List<MatchResultResponse>
) : MatchResultsResponse()

data class MatchResultResponse(
    @Json(name = "team1") val homeTeamName: String?,
    @Json(name = "team2") val awayTeamName: String?,
    @Json(name = "team1_points") val homeTeamScore: String?,
    @Json(name = "team2_points") val awayTeamScore: String?,
)

object MatchResultsFailureResponse : MatchResultsResponse()
