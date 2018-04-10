package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.model.Schedule
import br.com.disapps.domain.repository.SchedulesRepository
import io.reactivex.Single

class GetAllPointSchedules(val schedulesRepository: SchedulesRepository, val threadExecutor: ThreadExecutor,
                                           val postExecutionThread: PostExecutionThread): SingleUseCase<List<Schedule>, GetAllPointSchedules.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<List<Schedule>> {
        return schedulesRepository.getAllPointSchedules(params.codeLine, params.day, params.codePoint)
    }

    class Params(val codeLine: String, val day: Int, val codePoint : String)
}