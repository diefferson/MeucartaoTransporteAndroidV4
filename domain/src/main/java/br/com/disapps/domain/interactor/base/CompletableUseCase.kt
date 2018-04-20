package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import kotlinx.coroutines.experimental.*

/**
 * Abstract class for a CompletableUseCase that returns an instance of a [Completable].
 */
abstract class CompletableUseCase<in Params> internal constructor(
        private val contextExecutor: ContextExecutor,
        private val postExecutionContext: PostExecutionContext) {

    private val executionJob = Job()

    /**
     * Builds a [Completable] which will be used when the current [DefaultCompletableObserver] is executed.
     */
    internal abstract suspend fun buildUseCaseObservable(params: Params)

    /**
     * Executes the current use case.
     */
    fun execute(completableObserver: DefaultCompletableObserver, params: Params) {
        async(contextExecutor.scheduler, parent = executionJob) {
            try {
                buildUseCaseObservable(params)
                withContext(postExecutionContext.scheduler) {
                    completableObserver.onComplete()
                }
            } catch (e: Exception) {
                withContext(postExecutionContext.scheduler) {
                    completableObserver.onError(e)
                }
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