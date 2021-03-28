package com.tatar.presentation.feature.result.model

data class MatchResultsModel(
    val isEmpty: Boolean,
    val matchResults: List<MatchResultModel>
)

data class MatchResultModel(
    val matchIdentifier: String,
    val homeTeamName: String,
    val awayTeamName: String,
    val homeTeamScore: String,
    val awayTeamScore: String,
    val homeTeamScorePrediction: String,
    val awayTeamScorePrediction: String,
    val hasPrediction: Boolean
)
