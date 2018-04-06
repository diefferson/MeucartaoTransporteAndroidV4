package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.CompletableUseCase
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Completable
import br.com.disapps.domain.executor.PostExecutionThread

class SaveAllLinesJson(var linesRepository: LinesRepository, var threadExecutor: ThreadExecutor,
                       var postExecutionThread: PostExecutionThread): CompletableUseCase<SaveAllLinesJson.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params):Completable {
        return linesRepository.saveAllFromJson(params.json)
    }

    class Params (val json: String)

}