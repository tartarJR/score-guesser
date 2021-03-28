package com.tatar.scoreguesser.dagger

import android.content.Context
import com.tatar.core.dagger.qualifier.MainThread
import com.tatar.core.dagger.scope.ApplicationScope
import com.tatar.data.dagger.DataModule
import com.tatar.domain.dagger.DomainModule
import com.tatar.local.dagger.LocalModule
import com.tatar.presentation.dagger.module.ViewModelModule
import com.tatar.presentation.viewmodel.factory.ViewModelFactory
import com.tatar.remote.dagger.NetworkModule
import com.tatar.remote.dagger.RemoteModule
import dagger.BindsInstance
import dagger.Component
import io.reactivex.rxjava3.core.Scheduler

@ApplicationScope
@Component(
    modules = [
        ViewModelModule::class,
        DomainModule::class,
        DataModule::class,
        RemoteModule::class,
        NetworkModule::class,
        LocalModule::class
    ]
)
interface AppComponent {

    fun genericSavedStateViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
            @BindsInstance @MainThread mainScheduler: Scheduler,
        ): AppComponent
    }
}
