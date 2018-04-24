package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

class SaveCard (val cardRepository: CardsRepository, val contextExecutor: ContextExecutor,
                val postExecutionContext: PostExecutionContext) : UseCaseCompletable<SaveCard.Params>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: SaveCard.Params) {
        return cardRepository.saveCard(params.card)
    }

    class Params (val card: Card)
}