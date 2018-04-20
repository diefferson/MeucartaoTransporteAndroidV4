package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.model.Schedule
import br.com.disapps.domain.repository.SchedulesRepository

class GetAllPointSchedules(val schedulesRepository: SchedulesRepository, val contextExecutor: ContextExecutor,
                           val postExecutionContext: PostExecutionContext): BaseUseCase<List<Schedule>, GetAllPointSchedules.Params>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Params): List<Schedule> {
        return schedulesRepository.getAllPointSchedules(params.codeLine, params.day, params.codePoint)
    }

    class Params(val codeLine: String, val day: Int, val codePoint : String)
}