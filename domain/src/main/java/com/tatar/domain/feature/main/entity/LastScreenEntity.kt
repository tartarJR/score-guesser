package com.tatar.domain.feature.main.entity

data class LastScreenEntity(
    val lastScreen: String
) {
    fun isResultScreen(): Boolean {
        return lastScreen == SCREEN_RESULTS
    }

    companion object {
        internal const val SCREEN_PREDICTIONS = "PredictionsFragment"
        internal const val SCREEN_RESULTS = "ResultsFragment"
    }
}
