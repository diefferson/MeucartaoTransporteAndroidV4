package br.com.disapps.domain.interactor.base

open class DefaultSingleObserver<T>  {

    open fun onSuccess(t: T) {
        // no-op by default.
    }

    open fun onError(e: Throwable) {
        // no-op by default.
    }
}