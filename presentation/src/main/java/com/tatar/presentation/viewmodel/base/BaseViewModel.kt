package com.tatar.presentation.viewmodel.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatar.presentation.resource.navigation.Route
import com.tatar.presentation.resource.text.TextResource
import com.tatar.presentation.resource.text.TextResourceError
import com.tatar.presentation.util.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val _navigationEvent = MutableLiveData<Event<Route>>()
    private val _navigationBackEvent = MutableLiveData<Event<Unit>>()
    private val _showError = MutableLiveData<Event<TextResourceError>>()
    private val _showToast = MutableLiveData<Event<TextResource>>()

    val navigationEvent: LiveData<Event<Route>> get() = _navigationEvent
    val navigationBackEvent: LiveData<Event<Unit>> get() = _navigationBackEvent
    val showError: LiveData<Event<TextResourceError>> get() = _showError
    val showToast: LiveData<Event<TextResource>> get() = _showToast

    private val disposables = CompositeDisposable()

    protected fun navigateTo(route: Route) {
        _navigationEvent.postValue(Event(route))
    }

    protected fun navigateBack() {
        _navigationBackEvent.postValue(Event(Unit))
    }

    protected fun showError(textResourceError: TextResourceError) {
        _showError.postValue(Event(textResourceError))
    }

    protected fun showError(errorMessage: TextResource, errorInfo: String? = null) {
        _showError.postValue(Event(TextResourceError(errorMessage, errorInfo)))
    }

    protected fun showToast(textResource: TextResource) {
        _showToast.postValue(Event(textResource))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected fun clearCurrentDisposables() {
        disposables.clear()
    }

    fun dispose(vararg disposables: Disposable?) {
        for (disposable in disposables) {
            disposable?.dispose()
        }
    }

    fun Disposable.addToDisposables(): Disposable =
        apply { disposables.add(this) }
}
