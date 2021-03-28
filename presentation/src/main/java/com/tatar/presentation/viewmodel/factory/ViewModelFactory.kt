package com.tatar.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tatar.core.dagger.scope.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope
class ViewModelFactory @Inject constructor(
    private val providerMap: @JvmSuppressWildcards MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var provider: Provider<out ViewModel>? = providerMap[modelClass]
        if (provider == null) {
            for ((key, value) in providerMap) {
                if (modelClass.isAssignableFrom(key)) {
                    provider = value
                    break
                }
            }
        }
        if (provider == null) {
            throw IllegalArgumentException("Unknown model class: $modelClass")
        }

        try {
            @Suppress("UNCHECKED_CAST")
            return provider.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
