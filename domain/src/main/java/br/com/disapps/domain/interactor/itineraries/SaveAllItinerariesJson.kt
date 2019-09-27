package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ItinerariesRepository

class SaveAllItinerariesJson(val itinerariesRepository: ItinerariesRepository) : UseCase<Unit, SaveAllItinerariesJson.Params>(){

    override suspend fun run(params: Params) {
        return itinerariesRepository.saveAllFromJson(params.city, params.filePath)
    }

    class Params (val city : City,val filePath:String)
}