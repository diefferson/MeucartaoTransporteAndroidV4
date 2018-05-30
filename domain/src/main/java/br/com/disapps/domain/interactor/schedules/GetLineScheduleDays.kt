package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.SchedulesRepository

class GetLineScheduleDays (val schedulesRepository: SchedulesRepository, val contextExecutor: ContextExecutor,
                           val postExecutionContext: PostExecutionContext,
                           val logException: LogException): UseCase<List<Int>, GetLineScheduleDays.Params>(contextExecutor, postExecutionContext,logException) {

    override suspend fun buildUseCaseObservable(params: Params): List<Int> {
        return schedulesRepository.getLineSchedulesDays(params.codeLine)
    }

    class Params(val codeLine: String)
}