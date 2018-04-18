package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.CompletableUseCase
import br.com.disapps.domain.model.PeriodUpdate
import br.com.disapps.domain.repository.PreferencesRepository
import io.reactivex.Completable

class SavePeriodUpdateSchedules(private val preferencesRepository: PreferencesRepository, val threadExecutor: ThreadExecutor,
                                val postExecutionThread: PostExecutionThread): CompletableUseCase<SavePeriodUpdateSchedules.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Completable {
        return preferencesRepository.setPeriodUpdateSchedules(params.periodUpdate)
    }

    class Params(val periodUpdate: PeriodUpdate)
}