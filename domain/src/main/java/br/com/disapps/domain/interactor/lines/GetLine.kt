package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository

class GetLine(val linesRepository: LinesRepository, val contextExecutor: ContextExecutor,
              val postExecutionContext: PostExecutionContext,
              val logException: LogException): UseCase<Line, GetLine.Params>(contextExecutor, postExecutionContext,logException) {

    override suspend fun buildUseCaseObservable(params: Params): Line {
        return linesRepository.line(Line(params.codeLine, "", "", "",  false))
    }

    class Params(val codeLine:String)
}