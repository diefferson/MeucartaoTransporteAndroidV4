package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.withContext

/**
 * Abstract class for a TestCompletableUseCase that returns an instance of a [Single].
 */
abstract class TestSingleUseCase<T, in Params> internal constructor(
        private val contextExecutor: ContextExecutor,
        private val postExecutionContext: PostExecutionContext) {

    private val executionJob = Job()

    /**
     * Builds a [Single] which will be used when the current [DefaultSingleObserver] is executed.
     */
    internal abstract fun buildUseCaseObservable(params: Params): T

    /**
     * Executes the current use case.
     */
    open fun execute(singleObserver: DefaultSingleObserver<T>, params: Params) {
        async(contextExecutor.scheduler, parent = executionJob) {
            try {
                val response  = this@TestSingleUseCase.buildUseCaseObservable(params)

                withContext(postExecutionContext.scheduler) {
                    singleObserver.onSuccess(response)
                }

            } catch (e: Exception) {
                singleObserver.onError(e)
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