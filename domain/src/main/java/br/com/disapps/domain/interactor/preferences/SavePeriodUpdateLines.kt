package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.CompletableUseCase
import br.com.disapps.domain.model.PeriodUpdate
import br.com.disapps.domain.repository.PreferencesRepository
import io.reactivex.Completable

class SavePeriodUpdateLines(private val preferencesRepository: PreferencesRepository, val contextExecutor: ContextExecutor,
                            val postExecutionContext: PostExecutionContext): CompletableUseCase<SavePeriodUpdateLines.Params>(contextExecutor, postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params){
        return preferencesRepository.setPeriodUpdateLines(params.periodUpdate)
    }

    class Params(val periodUpdate: PeriodUpdate )
}