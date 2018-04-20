package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.repository.SchedulesRepository

class GetLineScheduleDays (val schedulesRepository: SchedulesRepository, val contextExecutor: ContextExecutor,
                           val postExecutionContext: PostExecutionContext): BaseUseCase<List<Int>, GetLineScheduleDays.Params>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Params): List<Int> {
        return schedulesRepository.getLineSchedulesDays(params.codeLine)
    }

    class Params(val codeLine: String)
}