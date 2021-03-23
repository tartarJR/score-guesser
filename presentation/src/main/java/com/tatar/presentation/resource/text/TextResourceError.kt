package com.tatar.presentation.resource.text

data class TextResourceError(
    val textResource: TextResource,
    val errorInfo: String? = null
)
