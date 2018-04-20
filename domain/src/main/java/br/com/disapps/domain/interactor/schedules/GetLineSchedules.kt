package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.domain.repository.SchedulesRepository

class GetLineSchedules(val schedulesRepository: SchedulesRepository, val contextExecutor: ContextExecutor,
                       val postExecutionContext: PostExecutionContext): BaseUseCase<List<LineSchedule>, GetLineSchedules.Params>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Params): List<LineSchedule> {
        return schedulesRepository.getLineSchedules(params.codeLine, params.day)
    }

    class Params(val codeLine: String, val day:Int)

}