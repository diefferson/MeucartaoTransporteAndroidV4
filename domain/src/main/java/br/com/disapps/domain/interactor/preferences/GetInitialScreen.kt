package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.repository.PreferencesRepository

class GetInitialScreen(private val preferencesRepository: PreferencesRepository, val contextExecutor: ContextExecutor,
                       val postExecutionContext: PostExecutionContext) : BaseUseCase<String, Unit>(contextExecutor, postExecutionContext) {


    override suspend fun buildUseCaseObservable(params: Unit): String {
        return preferencesRepository.getInitialScreen()
    }
}