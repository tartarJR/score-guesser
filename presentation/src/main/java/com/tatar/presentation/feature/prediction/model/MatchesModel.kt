package com.tatar.presentation.feature.prediction.model

data class MatchesModel(
    val isEmpty: Boolean,
    val matches: List<MatchModel>,
    val isResultsButtonVisible: Boolean
)

data class MatchModel(
    val matchIdentifier: String,
    val homeTeamName: String,
    val awayTeamName: String,
    val homeTeamScore: String = "",
    val awayTeamScore: String = ""
)
