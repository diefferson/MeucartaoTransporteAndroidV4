package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository

class GetLines(val linesRepository: LinesRepository, val contextExecutor: ContextExecutor,
               val postExecutionContext: PostExecutionContext): BaseUseCase<List<Line>, Unit>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Unit): List<Line> {
        return linesRepository.lines()
    }
}