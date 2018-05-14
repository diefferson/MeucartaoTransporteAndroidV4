package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.repository.SchedulesRepository

class InitSchedules(private val schedulesRepository: SchedulesRepository, val contextExecutor: ContextExecutor,
                    val postExecutionContext: PostExecutionContext) : UseCaseCompletable<Unit>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Unit) {
        return schedulesRepository.initSchedules()
    }
}
