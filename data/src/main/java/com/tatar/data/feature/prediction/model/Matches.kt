package com.tatar.data.feature.prediction.model

data class Matches(
    val matches: List<Match>
)

data class Match(
    val homeTeamName: String?,
    val awayTeamName: String?
)
