package com.tatar.domain.feature.prediction.entity

data class MatchesEntity(
    val matches: List<MatchEntity>
) {
    fun getFilteredMatches(): List<MatchEntity> {
        return matches.filter { it.isGameDataAvailable() }
            .sortedBy { it.homeTeamName }
    }

    fun hasPredictions(): Boolean {
        return getFilteredMatches().any { it.hasPredictions() }
    }
}

data class MatchEntity(
    val homeTeamName: String?,
    val awayTeamName: String?,
    val homeScorePrediction: String = "",
    val awayScorePrediction: String = ""
) {
    // Not really an identifier but works in this case, API is not the best
    fun getMatchIdentifier(): String {
        return "$homeTeamName-$awayTeamName"
    }

    fun getNonNullHomeTeamName(): String {
        if (homeTeamName.isNullOrBlank()) return ""

        return homeTeamName
    }

    fun getNonNullAwayTeamName(): String {
        if (awayTeamName.isNullOrBlank()) return ""

        return awayTeamName
    }

    fun isGameDataAvailable(): Boolean {
        return !homeTeamName.isNullOrBlank() && !awayTeamName.isNullOrBlank()
    }

    fun hasPredictions(): Boolean {
        return homeScorePrediction.isNotBlank() && awayScorePrediction.isNotBlank()
    }
}
