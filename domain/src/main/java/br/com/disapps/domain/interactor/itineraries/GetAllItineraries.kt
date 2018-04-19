package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.repository.ItinerariesRepository
import io.reactivex.Single

class GetAllItineraries(private val itinerariesRepository: ItinerariesRepository, val threadExecutor: ThreadExecutor,
                        val postExecutionThread: PostExecutionThread): SingleUseCase<List<BusStop>, GetAllItineraries.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<List<BusStop>> {
        return itinerariesRepository.getAllItineraries(params.codeLine)
    }

    class Params(val codeLine : String)
}