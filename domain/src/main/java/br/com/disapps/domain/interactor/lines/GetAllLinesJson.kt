package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Single
import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.listeners.DownloadProgressListener

class GetAllLinesJson(val linesRepository: LinesRepository, val threadExecutor: ThreadExecutor,
                      val postExecutionThread: PostExecutionThread) : SingleUseCase<String, GetAllLinesJson.Params>(threadExecutor,postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<String> {
        return linesRepository.jsonLines(params.downloadProgressListener)
    }

    class Params(val downloadProgressListener: DownloadProgressListener)
}
