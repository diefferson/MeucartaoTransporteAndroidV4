package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.CompletableUseCase
import br.com.disapps.domain.repository.SchedulesRepository
import io.reactivex.Completable

class SaveAllSchedulesJson(val schedulesRepository: SchedulesRepository, val threadExecutor: ThreadExecutor,
                           val postExecutionThread: PostExecutionThread) : CompletableUseCase<SaveAllSchedulesJson.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Completable {
        return schedulesRepository.saveAllFromJson(params.json)
    }

    class Params (val json: String)
}