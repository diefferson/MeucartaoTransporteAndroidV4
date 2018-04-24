package br.com.disapps.domain.interactor.base

open class UseCaseCompletableCallback{
    open fun onComplete() {
        // no-op by default.
    }

    open fun onError(e: Throwable) {
        // no-op by default.
    }
}