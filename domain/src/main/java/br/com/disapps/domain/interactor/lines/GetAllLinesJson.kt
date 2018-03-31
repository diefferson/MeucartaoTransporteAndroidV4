package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.UseCase
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Observable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread

class GetAllLinesJson(var linesRepository: LinesRepository, var threadExecutor: ThreadExecutor,
                      var postExecutionThread: PostExecutionThread) : UseCase<String, Unit>(threadExecutor,postExecutionThread){

    override fun buildUseCaseObservable(params: Unit): Observable<String> {
        return linesRepository.jsonLines()
    }
}
