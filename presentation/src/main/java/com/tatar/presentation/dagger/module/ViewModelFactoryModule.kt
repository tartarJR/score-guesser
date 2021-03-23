package com.tatar.presentation.dagger.module

import androidx.lifecycle.ViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import com.tatar.presentation.dagger.mapkey.ViewModelFactoryKey
import com.tatar.presentation.feature.result.ResultsViewModel
import com.tatar.presentation.viewmodel.factory.ViewModelAssistedFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@AssistedModule
@Module(includes = [AssistedInject_ViewModelFactoryModule::class])
abstract class ViewModelFactoryModule {

    @Binds
    @IntoMap
    @ViewModelFactoryKey(ResultsViewModel::class)
    internal abstract fun bindResultsViewModel(viewModelFactory: ResultsViewModel.Factory)
            : ViewModelAssistedFactory<out ViewModel>
}
