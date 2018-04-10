package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.CompletableUseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Completable
import br.com.disapps.domain.executor.PostExecutionThread

class SaveCard (val cardRepository: CardsRepository, val threadExecutor: ThreadExecutor,
                val postExecutionThread: PostExecutionThread) : CompletableUseCase<SaveCard.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: SaveCard.Params): Completable {
        return cardRepository.saveCard(params.card)
    }

    class Params (val card: Card)
}