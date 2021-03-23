package com.tatar.presentation.resource.navigation

sealed class Route(val shouldFinishHostActivity: Boolean = false)

object PredictionsToResults : Route()
