package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

class DeleteCard(val cardRepository: CardsRepository, val contextExecutor: ContextExecutor,
                 val postExecutionContext: PostExecutionContext,
                 val logException: LogException) : UseCaseCompletable<DeleteCard.Params>(contextExecutor, postExecutionContext, logException){


    override suspend fun buildUseCaseObservable(params: Params){
        return cardRepository.deleteCard(params.card)
    }

    class Params (val card: Card)
}