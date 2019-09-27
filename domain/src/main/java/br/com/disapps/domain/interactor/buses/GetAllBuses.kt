package br.com.disapps.domain.interactor.buses

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Bus
import br.com.disapps.domain.repository.BusesRepository

class GetAllBuses(private val busesRepository: BusesRepository) : UseCase<List<Bus>, GetAllBuses.Params>(){

    override suspend fun run(params: Params): List<Bus> {
        return busesRepository.getAllBuses(params.codeLine)
    }

    class Params(val codeLine : String)

}