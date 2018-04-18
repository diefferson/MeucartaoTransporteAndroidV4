package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.repository.ItinerariesRepository
import io.reactivex.Single

class GetItineraryDirections(private val itinerariesRepository: ItinerariesRepository, val threadExecutor: ThreadExecutor,
                             val postExecutionThread: PostExecutionThread): SingleUseCase<List<String>, GetItineraryDirections.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<List<String>> {
        return itinerariesRepository.getItineraryDirections(params.codeLine)
    }

    class Params(val codeLine : String)
}