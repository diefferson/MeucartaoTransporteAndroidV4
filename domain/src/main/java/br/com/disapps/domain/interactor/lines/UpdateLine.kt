package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository

class UpdateLine(val linesRepository: LinesRepository, val contextExecutor: ContextExecutor,
                 val postExecutionContext: PostExecutionContext,
                 val logException: LogException): UseCaseCompletable<UpdateLine.Params>(contextExecutor, postExecutionContext,logException){

    override suspend fun buildUseCaseObservable(params: Params) {
        return linesRepository.updateLine(params.line)
    }

    class Params (val line: Line)

}