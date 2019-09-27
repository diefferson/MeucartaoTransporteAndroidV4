package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.repository.ItinerariesRepository

class GetAllItineraries(private val itinerariesRepository: ItinerariesRepository): UseCase<List<BusStop>, GetAllItineraries.Params>(){

    override suspend fun run(params: Params): List<BusStop> {
        return itinerariesRepository.getAllItineraries(params.codeLine)
    }

    class Params(val codeLine : String)
}