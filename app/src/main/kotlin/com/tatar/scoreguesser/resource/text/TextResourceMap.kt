package com.tatar.scoreguesser.resource.text

import com.tatar.presentation.resource.text.TextResource
import com.tatar.scoreguesser.R

fun TextResource.resourceId(): Int {
    return when (this) {
        TextResource.GENERAL_ERROR_NETWORK -> R.string.general_error_network
    }
}
