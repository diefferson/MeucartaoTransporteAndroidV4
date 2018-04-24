package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import kotlinx.coroutines.experimental.*

/**
 * Abstract class for a UseCaseCompletable.
 */
abstract class UseCaseCompletable<in Params> internal constructor(
        private val contextExecutor: ContextExecutor,
        private val postExecutionContext: PostExecutionContext) {

    private val executionJob = Job()

    internal abstract suspend fun buildUseCaseObservable(params: Params)

    /**
     * Executes the current use case.
     */
    fun execute(completableCallback: UseCaseCompletableCallback, params: Params) {
        async(contextExecutor.scheduler, parent = executionJob) {
            try {
                buildUseCaseObservable(params)
                withContext(postExecutionContext.scheduler) {
                    completableCallback.onComplete()
                }
            } catch (e: Exception) {
                withContext(postExecutionContext.scheduler) {
                    completableCallback.onError(e)
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