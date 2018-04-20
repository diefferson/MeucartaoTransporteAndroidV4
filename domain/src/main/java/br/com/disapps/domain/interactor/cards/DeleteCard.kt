package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.CompletableCallback
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import br.com.disapps.domain.interactor.base.TestCompletableUseCase

class DeleteCard(val cardRepository: CardsRepository, val contextExecutor: ContextExecutor,
                 val postExecutionContext: PostExecutionContext) : TestCompletableUseCase<DeleteCard.Params>(contextExecutor, postExecutionContext){


    override fun buildUseCaseObservable(params: Params, callback: CompletableCallback){
        return cardRepository.deleteCard(params.card, callback)
    }

    class Params (val card: Card)
}