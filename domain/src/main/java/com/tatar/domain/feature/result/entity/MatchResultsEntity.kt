package com.tatar.domain.feature.result.entity

data class MatchResultsEntity(
    val matchResults: List<MatchResultEntity>
) {
    fun getFilteredMatchResults(): List<MatchResultEntity> {
        return matchResults.filter { it.isResultDataAvailable() }
            .sortedBy { it.homeTeamName }
    }
}

data class MatchResultEntity(
    val homeTeamName: String?,
    val awayTeamName: String?,
    val homeTeamScore: String?,
    val awayTeamScore: String?,
    val homeTeamPrediction: String? = "",
    val awayTeamPrediction: String? = ""
) {
    // Not really an identifier but works in this case, API is not the best
    fun getMatchIdentifier(): String {
        return "$homeTeamName-$awayTeamName"
    }

    fun getNonNullHomeTeamName(): String {
        if (homeTeamName.isNullOrBlank()) return ""

        return homeTeamName
    }

    fun getNonNullHomeTeamScore(): String {
        if (homeTeamScore.isNullOrBlank()) return ""

        return homeTeamScore
    }

    fun getNonNullHomeTeamPrediction(): String {
        if (homeTeamPrediction.isNullOrBlank()) return ""

        return homeTeamPrediction
    }

    fun getNonNullAwayTeamName(): String {
        if (awayTeamName.isNullOrBlank()) return ""

        return awayTeamName
    }

    fun getNonNullAwayTeamScore(): String {
        if (awayTeamScore.isNullOrBlank()) return ""

        return awayTeamScore
    }

    fun getNonNullAwayTeamPrediction(): String {
        if (awayTeamPrediction.isNullOrBlank()) return ""

        return awayTeamPrediction
    }

    fun isResultDataAvailable(): Boolean {
        return !homeTeamName.isNullOrBlank()
                && !awayTeamName.isNullOrBlank()
                && !homeTeamScore.isNullOrBlank()
                && !awayTeamScore.isNullOrBlank()
    }

    fun isPredictionDataAvailable(): Boolean {
        return !homeTeamPrediction.isNullOrBlank()
                && !awayTeamPrediction.isNullOrBlank()
    }
}
