package com.tatar.local.prefs

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.tatar.core.dagger.scope.ApplicationScope
import com.tatar.local.util.getLongFromSharedPreferences
import com.tatar.local.util.getStringFromSharedPreferences
import com.tatar.local.util.saveToSharedPreferences
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@ApplicationScope
class ScoreGuesserPrefs @Inject constructor(
    applicationContext: Context
) {

    private val sharedPreferences: SharedPreferences =
        applicationContext.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

    private val prefSubject: BehaviorSubject<SharedPreferences> =
        BehaviorSubject.createDefault(sharedPreferences)

    private val prefChangeListener: OnSharedPreferenceChangeListener =
        OnSharedPreferenceChangeListener { sharedPreferences, _ ->
            prefSubject.onNext(sharedPreferences)
        }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(prefChangeListener)
    }

    fun setLastScreen(lastScreen: String): Completable {
        return prefSubject.firstOrError().saveToSharedPreferences {
            putString(PREF_KEY_LAST_SCREEN, lastScreen)
        }
    }

    fun setLastRequestTime(lastRequestTime: Long): Completable {
        return prefSubject.firstOrError().saveToSharedPreferences {
            putLong(PREF_KEY_LAST_REQUEST_TIME, lastRequestTime)
        }
    }

    fun getLastScreen(): Maybe<String> {
        return prefSubject.firstOrError().getStringFromSharedPreferences(PREF_KEY_LAST_SCREEN)
    }

    fun getLastRequestTime(): Maybe<Long> {
        return prefSubject.firstOrError().getLongFromSharedPreferences(PREF_KEY_LAST_REQUEST_TIME)
    }

    fun clearPrefs() = sharedPreferences.edit().clear().apply()

    companion object {
        private const val PREFS_NAME = "score_guesser_prefs"
        private const val PREF_KEY_LAST_SCREEN = "PREF_KEY_LAST_SCREEN"
        private const val PREF_KEY_LAST_REQUEST_TIME = "PREF_KEY_LAST_REQUEST_TIME"
    }
}
