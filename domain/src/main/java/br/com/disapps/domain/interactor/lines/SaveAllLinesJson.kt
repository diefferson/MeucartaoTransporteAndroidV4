package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.Preconditions
import br.com.disapps.domain.interactor.UseCase
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Observable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread

class SaveAllLinesJson(var linesRepository: LinesRepository, var threadExecutor: ThreadExecutor,
                       var postExecutionThread: PostExecutionThread): UseCase<Boolean,SaveAllLinesJson.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Observable<Boolean> {
        Preconditions.checkNotNull(params)
        return linesRepository.saveAllFromJson(params.json)
    }


    class Params (val json: String)

}