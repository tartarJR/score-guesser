package com.tatar.scoreguesser.util

data class ListItemChange<out T>(
    val oldData: T,
    val newData: T
)

fun <T> createCombinedPayload(payloads: List<ListItemChange<T>>): ListItemChange<T> {
    val firstChange = payloads.first()
    val lastChange = payloads.last()

    return ListItemChange(firstChange.oldData, lastChange.newData)
}
