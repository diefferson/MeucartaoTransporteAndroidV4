package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.model.PeriodUpdate
import br.com.disapps.domain.repository.PreferencesRepository

class SavePeriodUpdateLines(private val preferencesRepository: PreferencesRepository, val contextExecutor: ContextExecutor,
                            val postExecutionContext: PostExecutionContext): UseCaseCompletable<SavePeriodUpdateLines.Params>(contextExecutor, postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params){
        return preferencesRepository.setPeriodUpdateLines(params.periodUpdate)
    }

    class Params(val periodUpdate: PeriodUpdate )
}