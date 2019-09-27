package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.PreferencesRepository

class SetIsPro(private val preferencesRepository: PreferencesRepository): UseCase<Unit, SetIsPro.Params>(){

    override suspend fun run(params: Params){
        return preferencesRepository.setIsPro(params.isPro)
    }

    class Params(val isPro : Boolean)
}