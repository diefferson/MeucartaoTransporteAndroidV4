package br.com.disapps.domain.interactor.itineraries


import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.repository.ItinerariesRepository

class GetItinerary(private val itinerariesRepository: ItinerariesRepository): UseCase<List<BusStop>, GetItinerary.Params>(){

    override suspend fun run(params: Params): List<BusStop> {
        return itinerariesRepository.getItinerary(params.codeLine, params.direction)
    }

    class Params(val codeLine : String, val direction: String)
}