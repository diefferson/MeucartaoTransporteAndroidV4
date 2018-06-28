package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

/**
 * Abstract class for a UseCaseCompletable that returns an instance of a [T].
 */
abstract class UseCase<T, in Params> internal constructor(
        private val contextExecutor: ContextExecutor,
        private val postExecutionContext: PostExecutionContext,
        private val logException: LogException) {

    private val executionJob = Job()

    /**
     * Builds a [T] which will be used when the current callback is executed.
     */
    internal abstract suspend fun buildUseCaseObservable(params: Params): T

    /**
     * Executes the current use case.
     */
    fun execute(params: Params,
                repeat: Boolean = false,
                repeatTime: Long = 30000,
                onError: (e: Throwable) -> Unit = {},
                onSuccess: (T) -> Unit = {}) {

        launch(contextExecutor.scheduler, parent = executionJob) {
            do{
                try {
                    val response  = buildUseCaseObservable(params)

                    withContext(postExecutionContext.scheduler) {
                        onSuccess(response)
                    }

                } catch (e: Exception) {
                    withContext(postExecutionContext.scheduler) {
                        logException.logException(e)
                        onError(e)
                    }
                }catch (e: OutOfMemoryError){
                    onError(KnownException(KnownError.OUT_OF_MEMORY_ERROR, e.message?:""))
                }

                if(repeat){
                    delay(repeatTime)
                }

            }while (repeat)
        }
    }

    /**
     * Dispose execution
     */
    fun dispose() {
        executionJob.cancel()
    }
}