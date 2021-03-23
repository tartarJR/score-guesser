package com.tatar.scoreguesser.feature.result

import com.tatar.core.dagger.scope.FragmentScope
import com.tatar.scoreguesser.dagger.AppComponent
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface ResultsComponent {
    fun inject(resultsFragment: ResultsFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ResultsComponent
    }
}
