package com.tatar.local.util

import android.content.SharedPreferences
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

fun Single<SharedPreferences>.saveToSharedPreferences(batch: SharedPreferences.Editor.() -> Unit): Completable =
    flatMapCompletable {
        Completable.fromAction {
            it.edit().also(batch).apply()
        }
    }

fun Single<SharedPreferences>.getStringFromSharedPreferences(prefsKey: String): Single<String> =
    flatMap {
        val stringPrefsValue = it.getString(prefsKey, null)

        if (stringPrefsValue == null)
            Single.error(IllegalArgumentException("Missing String value."))
        else
            Single.just(stringPrefsValue)
    }

fun Single<SharedPreferences>.getLongFromSharedPreferences(prefsKey: String): Single<Long> =
    flatMap {
        val longPrefsValue = it.getLong(prefsKey, -1L)

        if (longPrefsValue == -1L)
            Single.error(IllegalArgumentException("Missing Long value."))
        else
            Single.just(longPrefsValue)
    }
