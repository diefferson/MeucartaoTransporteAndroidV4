package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.UseCase
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Observable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread

class UpdateLine(var linesRepository: LinesRepository, var threadExecutor: ThreadExecutor,
                 var postExecutionThread: PostExecutionThread): UseCase<Boolean, UpdateLine.Params>(threadExecutor, postExecutionThread){


    override fun buildUseCaseObservable(params: Params): Observable<Boolean> {
        return linesRepository.updateLine(params.line)
    }

    class Params (val line: Line)

}