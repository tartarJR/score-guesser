package com.tatar.scoreguesser.util

data class ListItemChange<out T>(
    val oldData: T,
    val newData: T
)

