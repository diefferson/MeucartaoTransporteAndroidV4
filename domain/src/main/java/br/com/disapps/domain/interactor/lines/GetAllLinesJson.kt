package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Single
import br.com.disapps.domain.executor.PostExecutionThread

class GetAllLinesJson(var linesRepository: LinesRepository, var threadExecutor: ThreadExecutor,
                      var postExecutionThread: PostExecutionThread) : SingleUseCase<String, Unit>(threadExecutor,postExecutionThread){

    override fun buildUseCaseObservable(params: Unit): Single<String> {
        return linesRepository.jsonLines()
    }
}
