package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.executor.ThreadExecutor
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import br.com.disapps.domain.executor.PostExecutionThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver

/**
 * Abstract class for a ObservableUseCase that returns an instance of a [Completable].
 */
abstract class CompletableUseCase<in Params> internal constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread) {

    private val disposables = CompositeDisposable()

    /**
     * Builds a [Completable] which will be used when the current [CompletableUseCase] is executed.
     */
    internal abstract fun buildUseCaseObservable(params: Params): Completable

    /**
     * Executes the current use case.
     */
    fun execute(completableObserver: DisposableCompletableObserver, params: Params) {
        addDisposable(this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribeWith(completableObserver))
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}