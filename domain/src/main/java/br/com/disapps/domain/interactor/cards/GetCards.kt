package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

class GetCards(val cardRepository: CardsRepository, val contextExecutor: ContextExecutor,
               val postExecutionContext: PostExecutionContext,
               val logException: LogException) : UseCase<List<Card>, Unit>(contextExecutor, postExecutionContext,logException) {

    override suspend fun buildUseCaseObservable(params: Unit): List<Card> {
        return cardRepository.cards()
    }
}