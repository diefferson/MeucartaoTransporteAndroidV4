package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.CompletableUseCase
import br.com.disapps.domain.model.PeriodUpdate
import br.com.disapps.domain.repository.PreferencesRepository
import io.reactivex.Completable

class SavePeriodUpdateLines(private val preferencesRepository: PreferencesRepository, val threadExecutor: ThreadExecutor,
                            val postExecutionThread: PostExecutionThread): CompletableUseCase<SavePeriodUpdateLines.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Completable {
        return preferencesRepository.setPeriodUpdateLines(params.periodUpdate)
    }

    class Params(val periodUpdate: PeriodUpdate )
}