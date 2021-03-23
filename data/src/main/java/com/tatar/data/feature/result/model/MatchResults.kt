package com.tatar.data.feature.result.model

data class MatchResults(
    val matchResults: List<MatchResult>
)

data class MatchResult(
    val homeTeamName: String?,
    val awayTeamName: String?,
    val homeTeamScore: String?,
    val awayTeamScore: String?,
)
