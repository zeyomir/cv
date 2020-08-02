package io.github.zeyomir.cv.base.ui

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    private val onClearDisposables: CompositeDisposable = CompositeDisposable()
    private val onStopDisposables: CompositeDisposable = CompositeDisposable()

    fun disposeOnClear(disposable: Disposable) =
        onClearDisposables.add(disposable)

    fun disposeOnStop(disposable: Disposable) =
        onStopDisposables.add(disposable)

    override fun onCleared() {
        super.onCleared()
        onClearDisposables.clear()
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun beforeCreate() {
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun beforeStart() {
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun beforeStop() {
        onStopDisposables.clear()
    }
}
