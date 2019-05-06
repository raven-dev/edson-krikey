package com.ravn.edsonkrikey.core

import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

abstract class BaseViewModel(app: App) : AndroidViewModel(app) {

    internal val subscriptions by lazy { CompositeDisposable() }

    @CallSuper
    open fun onStart() {

    }

    @CallSuper
    open fun onViewCreated() {

    }

    @CallSuper
    open fun onViewDestroyed() {

    }

    @CallSuper
    open fun onStop() {
        subscriptions.clear()
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}