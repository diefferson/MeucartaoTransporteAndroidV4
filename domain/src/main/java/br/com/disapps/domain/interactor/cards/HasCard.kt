package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Single
import br.com.disapps.domain.executor.PostExecutionThread

class HasCard(var cardRepository: CardsRepository, var threadExecutor: ThreadExecutor,
              var postExecutionThread: PostExecutionThread) : SingleUseCase<Boolean, HasCard.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<Boolean> {
        return cardRepository.hasCard(params.card)
    }

    class Params (val card: Card)
}