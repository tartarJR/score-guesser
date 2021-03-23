package com.tatar.presentation.feature.prediction.model

data class MatchesModel(
    val isEmpty: Boolean,
    val matches: List<MatchModel>
)

data class MatchModel(
    val id: String,
    val homeTeamName: String?,
    val awayTeamName: String?
)
