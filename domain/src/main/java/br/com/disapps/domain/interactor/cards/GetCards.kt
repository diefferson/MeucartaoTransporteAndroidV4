package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Single
import br.com.disapps.domain.executor.PostExecutionThread

class GetCards(var cardRepository: CardsRepository, var threadExecutor: ThreadExecutor,
                var postExecutionThread: PostExecutionThread) : SingleUseCase<List<Card>, Unit>(threadExecutor, postExecutionThread) {


    override fun buildUseCaseObservable(params: Unit): Single<List<Card>> {
        return cardRepository.cards()
    }


}