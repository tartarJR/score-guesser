package com.tatar.scoreguesser.feature

import com.tatar.core.dagger.scope.ActivityScope
import com.tatar.scoreguesser.dagger.AppComponent
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): MainComponent
    }
}
