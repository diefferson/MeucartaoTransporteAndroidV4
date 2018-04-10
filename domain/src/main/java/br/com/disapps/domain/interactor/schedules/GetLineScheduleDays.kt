package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.repository.SchedulesRepository
import io.reactivex.Single

class GetLineScheduleDays (val schedulesRepository: SchedulesRepository, val threadExecutor: ThreadExecutor,
                           val postExecutionThread: PostExecutionThread): SingleUseCase<List<Int>, GetLineScheduleDays.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<List<Int>> {
        return schedulesRepository.getLineSchedulesDays(params.codeLine)
    }

    class Params(val codeLine: String)
}