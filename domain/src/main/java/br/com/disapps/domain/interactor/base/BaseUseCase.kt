package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.model.Bus
import kotlinx.coroutines.experimental.*

/**
 * Abstract class for a CompletableUseCase that returns an instance of a [Single].
 */
abstract class BaseUseCase<T, in Params> internal constructor(
        private val contextExecutor: ContextExecutor,
        private val postExecutionContext: PostExecutionContext) {

    private val executionJob = Job()

    /**
     * Builds a [Single] which will be used when the current [DefaultSingleObserver] is executed.
     */
    internal abstract suspend fun buildUseCaseObservable(params: Params): T

    /**
     * Executes the current use case.
     */
    open fun execute(singleObserver: DefaultSingleObserver<T>, params: Params, repeat:Boolean = false, repeatTime:Long = 30000) {

        async(contextExecutor.scheduler, parent = executionJob) {
            do{
                try {
                    val response  = buildUseCaseObservable(params)

                    withContext(postExecutionContext.scheduler) {
                        singleObserver.onSuccess(response)
                    }

                } catch (e: Exception) {
                    withContext(postExecutionContext.scheduler) {
                        singleObserver.onError(e)
                    }
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