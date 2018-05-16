package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
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
    fun execute(params: Params, onError: (e: Throwable) -> Unit = {}, onComplete: () -> Unit = {}) {
        launch(contextExecutor.scheduler, parent = executionJob) {
            try {
                buildUseCaseObservable(params)
                withContext(postExecutionContext.scheduler) {
                    onComplete()
                }
            } catch (e: Exception) {
                withContext(postExecutionContext.scheduler) {
                    onError(e)
                }
            }catch (e: OutOfMemoryError){
                onError(KnownException(KnownError.OUT_OF_MEMORY_ERROR, e.message?:""))
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