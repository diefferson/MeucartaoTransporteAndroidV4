package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.repository.SchedulesRepository

class SaveAllSchedulesJson(val schedulesRepository: SchedulesRepository, val contextExecutor: ContextExecutor,
                           val postExecutionContext: PostExecutionContext,val logException: LogException) : UseCaseCompletable<SaveAllSchedulesJson.Params>(contextExecutor, postExecutionContext,logException){

    override suspend fun buildUseCaseObservable(params: Params) {
        return schedulesRepository.saveAllFromJson(params.filePath, params.downloadProgressListener)
    }

    class Params (val filePath:String, val downloadProgressListener: DownloadProgressListener)
}