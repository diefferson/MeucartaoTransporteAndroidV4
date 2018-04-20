package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.CompletableCallback
import br.com.disapps.domain.interactor.base.TestCompletableUseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

class SaveCard (val cardRepository: CardsRepository, val contextExecutor: ContextExecutor,
                val postExecutionContext: PostExecutionContext) : TestCompletableUseCase<SaveCard.Params>(contextExecutor, postExecutionContext) {

    override fun buildUseCaseObservable(params: SaveCard.Params, callback: CompletableCallback) {
        return cardRepository.saveCard(params.card, callback)
    }

    class Params (val card: Card)
}