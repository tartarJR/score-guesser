package com.tatar.presentation.feature.main.model

sealed class LastScreenModel
object ResultsScreen : LastScreenModel()
object PredictionsScreen : LastScreenModel()
