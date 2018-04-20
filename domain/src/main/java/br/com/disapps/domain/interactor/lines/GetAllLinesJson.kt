package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.repository.LinesRepository

class GetAllLinesJson(val linesRepository: LinesRepository, val contextExecutor: ContextExecutor,
                      val postExecutionContext: PostExecutionContext) : BaseUseCase<String, GetAllLinesJson.Params>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Params): String {
        return linesRepository.jsonLines(params.downloadProgressListener)
    }

    class Params(val downloadProgressListener: DownloadProgressListener)
}
