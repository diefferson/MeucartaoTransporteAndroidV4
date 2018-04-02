package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.executor.ThreadExecutor
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import br.com.disapps.domain.executor.PostExecutionThread

/**
 * Abstract class for a ObservableUseCase that returns an instance of a [Single].
 */
abstract class SingleUseCase<T, in Params>internal constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread) {

    private val disposables = CompositeDisposable()

    /**
     * Builds a [Single] which will be used when the current [SingleUseCase] is executed.
     */
    internal abstract fun buildUseCaseObservable(params: Params): Single<T>

    /**
     * Executes the current use case.
     */
    open fun execute(singleObserver: DisposableSingleObserver<T>, params: Params) {
        Preconditions.checkNotNull(singleObserver)
        val single = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler) as Single<T>
        addDisposable(single.subscribeWith(singleObserver))
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
        Preconditions.checkNotNull(disposable)
        Preconditions.checkNotNull(disposables)
        disposables.add(disposable)
    }

}