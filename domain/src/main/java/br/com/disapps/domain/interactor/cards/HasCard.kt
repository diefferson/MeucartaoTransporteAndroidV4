package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

class HasCard(val cardRepository: CardsRepository, val contextExecutor: ContextExecutor,
              val postExecutionContext: PostExecutionContext,
              val logException: LogException) : UseCase<Boolean, HasCard.Params>(contextExecutor, postExecutionContext, logException){

    override suspend fun buildUseCaseObservable(params: Params): Boolean {
        return cardRepository.hasCard(params.card)
    }

    class Params (val card: Card)
}