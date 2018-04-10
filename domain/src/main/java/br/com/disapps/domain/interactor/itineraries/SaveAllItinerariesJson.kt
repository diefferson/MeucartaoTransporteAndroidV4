package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.CompletableUseCase
import br.com.disapps.domain.repository.ItinerariesRepository
import io.reactivex.Completable

class SaveAllItinerariesJson(val itinerariesRepository: ItinerariesRepository, val threadExecutor: ThreadExecutor,
                             val postExecutionThread: PostExecutionThread) : CompletableUseCase<SaveAllItinerariesJson.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Completable {
        return itinerariesRepository.saveAllFromJson(params.json)
    }

    class Params (val json: String)
}