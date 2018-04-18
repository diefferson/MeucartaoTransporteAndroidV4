package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.repository.SchedulesRepository
import io.reactivex.Single

class GetAllSchedulesJson(val schedulesRepository: SchedulesRepository, val threadExecutor: ThreadExecutor,
                          val postExecutionThread: PostExecutionThread): SingleUseCase<String, GetAllSchedulesJson.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<String> {
        return schedulesRepository.jsonSchedules(params.downloadProgressListener)
    }

    class Params(val downloadProgressListener: DownloadProgressListener)
}