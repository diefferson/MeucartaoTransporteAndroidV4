package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.ItinerariesRepository

class GetItineraryDirections(private val itinerariesRepository: ItinerariesRepository): UseCase<List<String>, GetItineraryDirections.Params>(){

    override suspend fun run(params: Params): List<String> {
        return itinerariesRepository.getItineraryDirections(params.codeLine)
    }

    class Params(val codeLine : String)
}