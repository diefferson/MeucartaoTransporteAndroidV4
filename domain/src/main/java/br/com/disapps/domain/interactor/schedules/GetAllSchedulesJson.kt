package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.repository.SchedulesRepository
import io.reactivex.Single

class GetAllSchedulesJson(val schedulesRepository: SchedulesRepository, val threadExecutor: ThreadExecutor,
                          val postExecutionThread: PostExecutionThread): SingleUseCase<String, Unit>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Unit): Single<String> {
        return schedulesRepository.jsonSchedules()
    }
}