package com.tatar.presentation.viewmodel.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class StatefulViewModel<State>(private val initialState: State) : BaseViewModel() {

    private val _state = MutableLiveData(initialState)
    val state: LiveData<State>
        get() = _state

    protected fun changeState(changeState: (currentState: State) -> State) {
        _state.value = changeState(_state.value ?: initialState)
    }
}
