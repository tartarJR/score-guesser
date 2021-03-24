package com.tatar.local.util

import android.content.SharedPreferences
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

fun Single<SharedPreferences>.saveToSharedPreferences(batch: SharedPreferences.Editor.() -> Unit): Completable =
    flatMapCompletable {
        Completable.fromAction {
            it.edit().also(batch).apply()
        }
    }

fun Single<SharedPreferences>.getStringFromSharedPreferences(prefsKey: String): Maybe<String> =
    flatMapMaybe {
        val stringPrefsValue = it.getString(prefsKey, null)

        if (stringPrefsValue == null)
            Maybe.empty()
        else
            Maybe.just(stringPrefsValue)
    }

fun Single<SharedPreferences>.getLongFromSharedPreferences(prefsKey: String): Maybe<Long> =
    flatMapMaybe {
        val longPrefsValue = it.getLong(prefsKey, -1L)

        if (longPrefsValue == -1L)
            Maybe.empty()
        else
            Maybe.just(longPrefsValue)
    }
