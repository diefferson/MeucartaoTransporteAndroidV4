package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.repository.PreferencesRepository

class SaveIsFirstAccess(private val preferencesRepository: PreferencesRepository, val contextExecutor: ContextExecutor,
                        val postExecutionContext: PostExecutionContext,
                        val logException: LogException): UseCaseCompletable<SaveIsFirstAccess.Params>(contextExecutor, postExecutionContext, logException){

    override suspend fun buildUseCaseObservable(params: Params){
        return preferencesRepository.setIsFirstAccess(params.isFirst)
    }

    class Params(val isFirst : Boolean)
}