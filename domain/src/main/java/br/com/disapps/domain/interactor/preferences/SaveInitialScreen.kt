package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.domain.repository.PreferencesRepository

class SaveInitialScreen(private val preferencesRepository: PreferencesRepository, val contextExecutor: ContextExecutor,
                        val postExecutionContext: PostExecutionContext) : UseCaseCompletable<SaveInitialScreen.Params>(contextExecutor, postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params){
        return preferencesRepository.setInitialScreen(params.initialScreen)
    }

    class Params(val initialScreen: InitialScreen)
}