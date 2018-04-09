package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Single
import br.com.disapps.domain.executor.PostExecutionThread

class GetLines(val linesRepository: LinesRepository, val threadExecutor: ThreadExecutor,
               val postExecutionThread: PostExecutionThread): SingleUseCase<List<Line>, Unit>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Unit): Single<List<Line>> {
        return linesRepository.lines()
    }
}