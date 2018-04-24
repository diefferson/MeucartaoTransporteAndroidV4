package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.repository.LinesRepository

class SaveAllLinesJson(val linesRepository: LinesRepository, val contextExecutor: ContextExecutor,
                       val postExecutionContext: PostExecutionContext): UseCaseCompletable<SaveAllLinesJson.Params>(contextExecutor, postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params) {
        return linesRepository.saveAllLinesFromJson(params.json)
    }

    class Params (val json: String)

}