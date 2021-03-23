package com.tatar.domain.feature.result.entity

data class MatchResultsEntity(
    val matchResults: List<MatchResultEntity>
)

data class MatchResultEntity(
    val homeTeamName: String?,
    val awayTeamName: String?,
    val homeTeamScore: String?,
    val awayTeamScore: String?,
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

    fun getNonNullAwayTeamScore(): String {
        if (awayTeamScore.isNullOrBlank()) return ""

        return awayTeamScore
    }

    private fun isResultDataAvailable(): Boolean {
        return !homeTeamName.isNullOrBlank()
                && !awayTeamName.isNullOrBlank()
                && !homeTeamScore.isNullOrBlank()
                && !awayTeamScore.isNullOrBlank()
    }
}
