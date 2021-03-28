package com.tatar.presentation.feature.main.mapper

import com.tatar.domain.feature.main.entity.LastScreenEntity
import com.tatar.presentation.feature.main.model.LastScreenModel
import com.tatar.presentation.feature.main.model.PredictionsScreen
import com.tatar.presentation.feature.main.model.ResultsScreen

internal object LastScreenEntityMapper {
    fun mapToModel(lastScreenEntity: LastScreenEntity): LastScreenModel {
        return if (lastScreenEntity.isResultScreen()) ResultsScreen
        else PredictionsScreen
    }
}
