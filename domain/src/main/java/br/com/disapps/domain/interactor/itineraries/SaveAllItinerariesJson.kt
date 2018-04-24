package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ItinerariesRepository

class SaveAllItinerariesJson(val itinerariesRepository: ItinerariesRepository, val contextExecutor: ContextExecutor,
                             val postExecutionContext: PostExecutionContext) : UseCaseCompletable<SaveAllItinerariesJson.Params>(contextExecutor, postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params) {
        return itinerariesRepository.saveAllFromJson(params.json, params.city)
    }

    class Params (val json: String, val city : City)
}