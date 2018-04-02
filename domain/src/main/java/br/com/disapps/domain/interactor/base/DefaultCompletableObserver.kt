package br.com.disapps.domain.interactor.base

import io.reactivex.observers.DisposableCompletableObserver

open class DefaultCompletableObserver : DisposableCompletableObserver() {

    override fun onComplete() {
        // no-op by default.
    }

    override fun onError(e: Throwable) {
        // no-op by default.
    }
}