package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import kotlinx.coroutines.experimental.*

/**
 * Abstract class for a TestCompletableUseCase that returns an instance of a [Completable].
 */
abstract class TestCompletableUseCase<in Params> internal constructor(
        private val contextExecutor: ContextExecutor,
        private val postExecutionContext: PostExecutionContext) {

    private val executionJob = Job()

    /**
     * Builds a [Completable] which will be used when the current [DefaultCompletableObserver] is executed.
     */
    internal abstract fun buildUseCaseObservable(params: Params, callback: CompletableCallback)

    /**
     * Executes the current use case.
     */
    fun execute(completableObserver: DefaultCompletableObserver, params: Params) {
        async(contextExecutor.scheduler, parent = executionJob) {
            try {
                buildUseCaseObservable(params, object : CompletableCallback{
                    override fun onComplete() {
                        launch(postExecutionContext.scheduler){
                            completableObserver.onComplete()
                        }
                    }
                    override fun onError(error: Throwable) {
                        launch(postExecutionContext.scheduler){
                            completableObserver.onError(error)
                        }
                    }
                })

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