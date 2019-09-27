package br.com.disapps.domain.interactor.preferences


import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.DataUsage
import br.com.disapps.domain.repository.PreferencesRepository

class GetDataUsage(private val preferencesRepository: PreferencesRepository) : UseCase<DataUsage, UseCase.None>() {

    override suspend fun run(params: None):DataUsage {
        return preferencesRepository.getDataUsage()
    }
}