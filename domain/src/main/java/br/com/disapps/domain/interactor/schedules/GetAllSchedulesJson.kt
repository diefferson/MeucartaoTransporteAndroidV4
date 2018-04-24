package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.repository.SchedulesRepository

class GetAllSchedulesJson(val schedulesRepository: SchedulesRepository, val contextExecutor: ContextExecutor,
                          val postExecutionContext: PostExecutionContext): UseCase<String, GetAllSchedulesJson.Params>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Params): String {
        return schedulesRepository.jsonSchedules(params.downloadProgressListener)
    }

    class Params(val downloadProgressListener: DownloadProgressListener)
}