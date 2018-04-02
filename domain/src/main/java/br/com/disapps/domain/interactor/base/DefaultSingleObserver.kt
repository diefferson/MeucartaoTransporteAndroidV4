package br.com.disapps.domain.interactor.base

import io.reactivex.observers.DisposableSingleObserver

open class DefaultSingleObserver<T> : DisposableSingleObserver<T>() {

    override fun onSuccess(t: T) {
        // no-op by default.
    }

    override fun onError(e: Throwable) {
        // no-op by default.
    }
}