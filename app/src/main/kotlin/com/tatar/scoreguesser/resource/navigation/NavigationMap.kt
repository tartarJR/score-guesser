package com.tatar.scoreguesser.resource.navigation

import androidx.navigation.NavDirections
import com.tatar.presentation.resource.navigation.PredictionsToResults
import com.tatar.presentation.resource.navigation.Route
import com.tatar.scoreguesser.feature.prediction.PredictionsFragmentDirections

fun Route.mapToNavigationResId(): NavDirections {
    return when (this) {
        PredictionsToResults -> PredictionsFragmentDirections.actionPredictionsFragmentToResultsFragment()
    }
}
