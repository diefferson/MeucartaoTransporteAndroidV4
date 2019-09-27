package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.domain.repository.PreferencesRepository

class SaveInitialScreen(private val preferencesRepository: PreferencesRepository) : UseCase<Unit, SaveInitialScreen.Params>(){

    override suspend fun run(params: Params){
        return preferencesRepository.setInitialScreen(params.initialScreen)
    }

    class Params(val initialScreen: InitialScreen)
}