package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.domain.model.Schedule
import br.com.disapps.domain.repository.SchedulesRepository
import io.reactivex.Single

class GetLineSchedules(val schedulesRepository: SchedulesRepository, val threadExecutor: ThreadExecutor,
                       val postExecutionThread: PostExecutionThread): SingleUseCase<List<LineSchedule>, GetLineSchedules.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<List<LineSchedule>> {
        return schedulesRepository.getLineSchedules(params.codeLine, params.day)
    }

    class Params(val codeLine: String, val day:Int)

}