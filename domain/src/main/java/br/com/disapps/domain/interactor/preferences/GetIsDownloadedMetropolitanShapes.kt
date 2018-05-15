package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.PreferencesRepository

class GetIsDownloadedMetropolitanShapes (private val preferencesRepository: PreferencesRepository, val contextExecutor: ContextExecutor,
                                         val postExecutionContext: PostExecutionContext): UseCase<Boolean, Unit>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Unit): Boolean {
        return preferencesRepository.getIsDownloadedMetropolitanShapes()
    }
}