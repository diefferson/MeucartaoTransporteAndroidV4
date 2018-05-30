package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.PreferencesRepository

class GetIsDownloadedCwbShapes (private val preferencesRepository: PreferencesRepository, val contextExecutor: ContextExecutor,
                                val postExecutionContext: PostExecutionContext,
                                val logException: LogException): UseCase<Boolean, Unit>(contextExecutor, postExecutionContext,logException) {

    override suspend fun buildUseCaseObservable(params: Unit): Boolean {
        return preferencesRepository.getIsDownloadedCwbShapes()
    }
}