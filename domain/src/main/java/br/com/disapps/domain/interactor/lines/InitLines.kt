package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.repository.LinesRepository

class InitLines(private val linesRepository: LinesRepository, val contextExecutor: ContextExecutor,
                      val postExecutionContext: PostExecutionContext) : UseCaseCompletable<Unit>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Unit) {
        return linesRepository.initLines()
    }
}
