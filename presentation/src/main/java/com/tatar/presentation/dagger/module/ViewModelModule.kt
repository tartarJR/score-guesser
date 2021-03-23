package com.tatar.presentation.dagger.module

import androidx.lifecycle.ViewModel
import com.tatar.presentation.dagger.mapkey.ViewModelKey
import com.tatar.presentation.feature.prediction.PredictionsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PredictionsViewModel::class)
    internal abstract fun predictionsViewModel(viewModel: PredictionsViewModel): ViewModel
}
