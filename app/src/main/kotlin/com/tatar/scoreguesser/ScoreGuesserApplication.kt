package com.tatar.scoreguesser

import android.app.Application
import com.tatar.scoreguesser.dagger.AppComponent
import com.tatar.scoreguesser.dagger.DaggerAppComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

class ScoreGuesserApplication : Application() {

    val appComponent: AppComponent by lazy { initializeAppComponent() }

    private fun initializeAppComponent(): AppComponent {
        return DaggerAppComponent.factory()
            .create(this, AndroidSchedulers.mainThread())
    }

    override fun onCreate() {
        super.onCreate()

        setupLogger()
    }

    private fun setupLogger() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
