package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.repository.LinesRepository

class SaveAllLinesJson(val linesRepository: LinesRepository, val contextExecutor: ContextExecutor,
                       val postExecutionContext: PostExecutionContext,
                       val logException: LogException): UseCaseCompletable<SaveAllLinesJson.Params>(contextExecutor, postExecutionContext,logException){

    override suspend fun buildUseCaseObservable(params: Params) {
        return linesRepository.saveAllLinesFromJson(params.filePath, params.downloadProgressListener)
    }

    class Params (val filePath:String, val downloadProgressListener: DownloadProgressListener)

}