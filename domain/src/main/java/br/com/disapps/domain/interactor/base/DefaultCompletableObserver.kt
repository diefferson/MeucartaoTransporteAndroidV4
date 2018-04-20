package br.com.disapps.domain.interactor.base

open class DefaultCompletableObserver{
    open fun onComplete() {
        // no-op by default.
    }

    open fun onError(e: Throwable) {
        // no-op by default.
    }
}