package com.tatar.presentation.viewmodel.factory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface ViewModelAssistedFactory<V : ViewModel> {
    fun create(handle: SavedStateHandle): V
}
