package br.com.disapps.domain.interactor.preferences


import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.PreferencesRepository

class GetInitialScreen(private val preferencesRepository: PreferencesRepository) : UseCase<String, UseCase.None>() {

    override suspend fun run(params: None): String {
        return preferencesRepository.getInitialScreen()
    }
}