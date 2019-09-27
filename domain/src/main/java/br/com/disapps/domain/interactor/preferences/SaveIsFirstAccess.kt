package br.com.disapps.domain.interactor.preferences


import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.PreferencesRepository

class SaveIsFirstAccess(private val preferencesRepository: PreferencesRepository): UseCase<Unit, SaveIsFirstAccess.Params>(){

    override suspend fun run(params: Params){
        return preferencesRepository.setIsFirstAccess(params.isFirst)
    }

    class Params(val isFirst : Boolean)
}