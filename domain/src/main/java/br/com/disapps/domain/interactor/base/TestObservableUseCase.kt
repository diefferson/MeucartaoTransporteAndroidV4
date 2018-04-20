package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import io.reactivex.Observable
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.withContext
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Abstract class for a TestCompletableUseCase that returns an instance of a [Observable].
 */
abstract class TestObservableUseCase<T, in Params> internal constructor(
        private val contextExecutor: ContextExecutor,
        private val postExecutionContext: PostExecutionContext) {

    private val executionJob = Job()

    /**
     * Builds an [Observable] which will be used when executing the current [ObservableUseCase].
     */
    internal abstract fun buildUseCaseObservable(params: Params): T

    /**
     * Executes the current use case.
     */
    open fun execute(observer: DefaultObserver<T>, params: Params) {
        async(contextExecutor.scheduler, parent = executionJob) {
            try {
                val response  = this@TestObservableUseCase.buildUseCaseObservable(params)

                withContext(postExecutionContext.scheduler) {
                    observer.onNext(response)
                }

            } catch (e: Exception) {
                observer.onError(e)
            }
        }
    }

    /**
     * Dispose execution
     */
    fun dispose() {
        executionJob.cancel()
    }
}