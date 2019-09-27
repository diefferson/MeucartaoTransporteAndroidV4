package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.PreferencesRepository

class GetIsDownloadedCwbItineraries (private val preferencesRepository: PreferencesRepository): UseCase<Boolean, UseCase.None>() {

    override suspend fun run(params: None): Boolean {
        return preferencesRepository.getIsDownloadedCwbItineraries()
    }
}