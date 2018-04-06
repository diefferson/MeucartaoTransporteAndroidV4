package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.CompletableUseCase
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Completable
import br.com.disapps.domain.executor.PostExecutionThread

class UpdateLine(var linesRepository: LinesRepository, var threadExecutor: ThreadExecutor,
                 var postExecutionThread: PostExecutionThread): CompletableUseCase<UpdateLine.Params>(threadExecutor, postExecutionThread){


    override fun buildUseCaseObservable(params: Params): Completable {
        return linesRepository.updateLine(params.line)
    }

    class Params (val line: Line)

}