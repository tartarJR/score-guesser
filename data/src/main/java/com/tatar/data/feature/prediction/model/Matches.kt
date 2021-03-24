package com.tatar.data.feature.prediction.model

data class Matches(
    val matches: List<Match>
)

data class Match(
    val matchIdentifier: String = "",
    val homeTeamName: String?,
    val awayTeamName: String?,
    val homeScorePrediction: String = "",
    val awayScorePrediction: String = ""
)
