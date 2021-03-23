package com.tatar.scoreguesser.feature.prediction

import com.tatar.core.dagger.scope.FragmentScope
import com.tatar.scoreguesser.dagger.AppComponent
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface PredictionsComponent {
    fun inject(predictionsFragment: PredictionsFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): PredictionsComponent
    }
}
