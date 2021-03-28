package com.tatar.scoreguesser.resource.text

import com.tatar.presentation.resource.text.TextResource
import com.tatar.scoreguesser.R

fun TextResource.resourceId(): Int {
    return when (this) {
        TextResource.GENERAL_ERROR_NETWORK -> R.string.general_error_network
        TextResource.SAVE_PREDICTION_ERROR -> R.string.predictions_error_save
        TextResource.SAVE_PREDICTION_SUCCESS -> R.string.predictions_success_save
    }
}
