package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.repository.PreferencesRepository

class SetIsPro(private val preferencesRepository: PreferencesRepository, val contextExecutor: ContextExecutor,
                        val postExecutionContext: PostExecutionContext): UseCaseCompletable<SetIsPro.Params>(contextExecutor, postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params){
        return preferencesRepository.setIsPro(params.isPro)
    }

    class Params(val isPro : Boolean)
}